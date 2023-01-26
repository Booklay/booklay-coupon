package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointCouponRetrieveResponse {
    private String name;
    private int amount;
}
