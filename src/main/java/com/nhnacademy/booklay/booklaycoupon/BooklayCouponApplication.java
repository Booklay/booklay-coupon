package com.nhnacademy.booklay.booklaycoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableDiscoveryClient
public class BooklayCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooklayCouponApplication.class, args);
    }

}
