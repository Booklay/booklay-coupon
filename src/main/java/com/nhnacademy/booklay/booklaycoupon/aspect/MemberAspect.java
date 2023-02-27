package com.nhnacademy.booklay.booklaycoupon.aspect;

import com.nhnacademy.booklay.booklaycoupon.dto.ApiEntity;
import com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.member.response.MemberRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 로그인한 멤버의 정보를 받아오기 위한 AOP.
 * Controller 클래스에서 메소드 파라미터에 MemberInfo를 넣으면 주입됩니다.
 *
 * @author 조현진
 */
@Slf4j
@Aspect
@Order(value = 100)
@Component
@RequiredArgsConstructor
public class MemberAspect {

    private final TokenUtils tokenUtils;
    private final RestService restService;
    @Around("@within(restController) && execution(* *.*(.., com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo, ..))")
    public Object injectMember(ProceedingJoinPoint pjp, RestController restController) throws Throwable {
        log.info("Method: {}", pjp.getSignature().getName());
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        MemberInfo memberInfo;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization != null){
            String email = tokenUtils.getEmail(authorization.substring("Bearer ".length()));
            String url = tokenUtils.getShopUrl() + email;
            ApiEntity<MemberRetrieveResponse> memberRetrieveResponse =
                    restService.get(url, null, new ParameterizedTypeReference<>() {
                    });
            memberInfo = memberRetrieveResponse == null ? new MemberInfo() :
                    new MemberInfo(memberRetrieveResponse.getBody());
        }else {
            memberInfo = new MemberInfo();
        }

        MemberInfo finalMemberInfo = memberInfo;
        Object[] args = Arrays.stream(pjp.getArgs()).map(arg -> {
            if (arg instanceof MemberInfo) {
                arg = finalMemberInfo;
            }
            return arg;
        }).toArray();


        return pjp.proceed(args);
    }

}
