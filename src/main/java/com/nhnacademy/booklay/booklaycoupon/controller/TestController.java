package com.nhnacademy.booklay.booklaycoupon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/coupon/1")
    public String retrieveCoupons() {
        return "this is coupon 1";
    }

    @GetMapping("/coupon/2")
    public String retrieveCoupons2() {
        return "this is coupon 2";
    }

    @GetMapping("/coupon/3")
    public String retrieveCoupons3() {
        return "this is coupon 3";
    }
}
