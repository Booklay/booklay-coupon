package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponCURequest {
    @NotBlank
    @Size(max = 100)
    private String name;

    private Long fileId;

    @NotNull
    private Long typeCode;

    @NotNull
    private Integer amount;

    @NotNull
    private Boolean isOrderCoupon;

    private Long applyItemId;

    @NotNull
    private int minimumUseAmount;

    private int maximumDiscountAmount;

    @NotNull
    private Boolean isDuplicatable;

    @NotNull
    private Boolean isLimited;

    public static Coupon toEntity(CouponCURequest couponRequest, CouponType couponType) {
        return Coupon.builder()
            .couponType(couponType)
            .name(couponRequest.getName())
            .amount(couponRequest.getAmount())
            .minimumUseAmount(couponRequest.getMinimumUseAmount())
            .maximumDiscountAmount(couponRequest.getMaximumDiscountAmount())
            .isDuplicatable(couponRequest.getIsDuplicatable())
            .isLimited(couponRequest.getIsLimited())
            .build();
    }
}
