package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponGeneralService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("coupons")
public class CouponRestController {

    private final CouponGeneralService couponGeneralService;
    @GetMapping("code")
    public ResponseEntity<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCode(
            @RequestParam(value = "couponCode") String couponCode){
        CouponRetrieveResponseFromProduct couponRetrieveResponse = couponGeneralService.retrieveCouponByCouponCode(couponCode);
        return ResponseEntity.ok(couponRetrieveResponse);
    }
}
