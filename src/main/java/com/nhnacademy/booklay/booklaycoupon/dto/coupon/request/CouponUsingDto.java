package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import lombok.Getter;

@Getter
public class CouponUsingDto {
    private String couponCode;
    private Long specifiedCouponNo;
    private Long usedTargetNo;

}
