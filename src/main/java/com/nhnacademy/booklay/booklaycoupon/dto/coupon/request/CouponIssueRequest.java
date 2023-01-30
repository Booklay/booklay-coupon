package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueRequest {

    @NotNull
    Long couponId;

    @NotNull
    @Min(1)
    int quantity;
}