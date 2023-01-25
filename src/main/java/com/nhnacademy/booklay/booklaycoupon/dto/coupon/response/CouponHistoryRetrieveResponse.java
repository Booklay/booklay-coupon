package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponHistoryRetrieveResponse {
    private final Long id;
    private final String name;
}
