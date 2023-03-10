package com.nhnacademy.booklay.booklaycoupon.dto.coupon.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueRequestMessage {
    private Long couponId;
    private Long memberId;
    private String uuid;
}
