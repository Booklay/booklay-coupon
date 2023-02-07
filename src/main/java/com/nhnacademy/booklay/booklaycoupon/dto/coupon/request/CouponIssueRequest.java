package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 쿠폰 대량 발급에 필요한 객체입니다.
 * 대량 발급시에는, 사용자가 쿠폰을 발급을 받을 때 만료일자가 정해집니다.
 */
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