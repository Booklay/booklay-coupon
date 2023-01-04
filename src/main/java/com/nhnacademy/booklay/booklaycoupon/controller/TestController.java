package com.nhnacademy.booklay.booklaycoupon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/coupon")
    public String retrieveCoupons() {
        return "coupon 123";
    }
}
