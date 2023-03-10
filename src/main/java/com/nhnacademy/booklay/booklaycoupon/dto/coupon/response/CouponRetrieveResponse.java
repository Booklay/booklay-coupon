package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponRetrieveResponse {
    private final Long id;
    private final String name;
    private final String typeName;
    private final int amount;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final Boolean isLimited;

    @Builder
    public CouponRetrieveResponse(Long id, String name, CouponType couponType, int amount,
                                  int minimumUseAmount, int maximumDiscountAmount,
                                  Boolean isLimited) {
        this.id = id;
        this.name = name;
        this.typeName = couponType.getName();
        this.amount = amount;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.isLimited = isLimited;
    }

    public static CouponRetrieveResponse fromEntity(Coupon coupon) {
        return CouponRetrieveResponse.builder()
            .id(coupon.getId())
            .name(coupon.getName())
            .couponType(coupon.getCouponType())
            .amount(coupon.getAmount())
            .minimumUseAmount(coupon.getMinimumUseAmount())
            .minimumUseAmount(coupon.getMaximumDiscountAmount())
            .isLimited(coupon.getIsLimited())
            .build();
    }
}
