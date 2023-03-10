package com.nhnacademy.booklay.booklaycoupon.config;

import com.nhnacademy.booklay.booklaycoupon.dto.secrets.DatasourceInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.secrets.SecretResponse;
import com.nhnacademy.booklay.booklaycoupon.filter.ContentCachingRequestWrapperFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Objects;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

/**
 * Web에 관한 전반적인 설정을 관리합니다.
 *
 * @author 조현진
 */
@Configuration
public class WebConfig {

    @Value("${booklay.secure.p12_password}")
    private String p12Password;

    @Value("${booklay.secure.url}")
    private String url;

    @Value("${booklay.secure.db_username}")
    private String username;

    @Value("${booklay.secure.db_password}")
    private String password;

    @Value("${booklay.secure.db_url}")
    private String dbUrl;
    @Bean
    @Primary
    public String gatewayIp(@Value("${booklay.gateway-origin}") String ip) {
        return ip;
    }
    /**
     * NHN Secure Manager를 통해 민감정보를 받습니다.
     * booklay.p12인증서를 통해 HTTPS 요청을 보냅니다.
     *
     * @author 조현진
     */
    @Bean
    public RestTemplate restTemplate() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {
        var clientStore = KeyStore.getInstance("PKCS12");

        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("booklay.p12")) {
            FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString("rw-------"));

            Path tempFile = Files.createTempFile(String.valueOf(inputStream.hashCode()), ".tmp", attr); // Compliant, created with explicit attributes.

            tempFile.toFile().deleteOnExit();


            copyInputStreamToFile(inputStream, tempFile.toFile());

            clientStore.load(new FileInputStream(tempFile.toFile()), p12Password.toCharArray());
        }

        var sslContext = SSLContextBuilder.create()
                .setProtocol("TLS")
                .loadKeyMaterial(clientStore, p12Password.toCharArray())
                .loadTrustMaterial(new TrustSelfSignedStrategy())
                .build();

        var sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }

    /**
     * 애플리케이션이 구동되는 동시에 NHN Secure Manager로부터 접속 정보를 가져옵니다.
     *
     * @param restTemplate
     * @return DB 접속 정보를 반환합니다.
     *
     * @author 조현진
     */
    @Bean
    public DatasourceInfo datasourceInfo(RestTemplate restTemplate) {
        SecretResponse
            usernameResponse = Objects.requireNonNull(restTemplate.getForObject(url + this.username, SecretResponse.class));
        SecretResponse passwordResponse = Objects.requireNonNull(restTemplate.getForObject(url + this.password, SecretResponse.class));
        SecretResponse dbUrlResponse = Objects.requireNonNull(restTemplate.getForObject(url + this.dbUrl, SecretResponse.class));

        return DatasourceInfo.builder()
                .password(passwordResponse.getBody().getSecret())
                .dbUrl(dbUrlResponse.getBody().getSecret())
                .username(usernameResponse.getBody().getSecret())
                .build();

    }

    /**
     * inputStream 을 여러번 읽을 수 있는 requestWrapper 로 변환하는 필터
     */
    @Bean
    public FilterRegistrationBean<ContentCachingRequestWrapperFilter> reReadableRequestFilter(){
        return new FilterRegistrationBean<>(new ContentCachingRequestWrapperFilter());
    }
}
