package com.nhnacademy.booklay.booklaycoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BooklayCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooklayCouponApplication.class, args);
    }

}
