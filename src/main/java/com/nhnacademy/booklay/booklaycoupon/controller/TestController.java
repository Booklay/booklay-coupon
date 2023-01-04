package com.nhnacademy.booklay.booklaycoupon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/coupon")
    public String retrieveCoupons() {
        return "this is coupon 1";
    }

    @GetMapping("/coupon2")
    public String retrieveCoupons2() {
        return "this is coupon 2";
    }

    @GetMapping("/coupon3")
    public String retrieveCoupons3() {
        return "this is coupon 3";
    }
}
