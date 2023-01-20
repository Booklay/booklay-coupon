package com.nhnacademy.booklay.booklaycoupon.service;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;

public interface CouponComplexService {
    void createAndIssueCouponByTemplate(CouponTemplate couponTemplate, Long memberId);
}
