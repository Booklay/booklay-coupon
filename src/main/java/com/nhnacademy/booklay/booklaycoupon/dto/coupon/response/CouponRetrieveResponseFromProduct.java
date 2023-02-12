package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponRetrieveResponseFromProduct {
    private final Long id;
    private final String name;
    private final String typeName;
    private final int amount;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final Boolean isLimited;
    private final String couponCode;
    private final Long categoryNo;
    private final Long productNo;

    @Builder
    public CouponRetrieveResponseFromProduct(String code, Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.typeName = coupon.getCouponType().getName();
        this.amount = coupon.getAmount();
        this.minimumUseAmount = coupon.getMinimumUseAmount();
        this.maximumDiscountAmount = coupon.getMaximumDiscountAmount();
        this.isLimited = coupon.getIsLimited();
        this.couponCode = code;
        this.categoryNo = coupon.getCategoryNo();
        this.productNo = coupon.getProductNo();
    }

}
