package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponZoneIssueToMemberRequest {

    @NotNull
    private final Long couponId;

    @NotNull
    private final Long memberId;
}