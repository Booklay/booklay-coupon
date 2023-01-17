package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponIssueToMemberRequest {

    @NotNull
    private final Long couponId;

    @NotNull
    private final Long memberId;
}