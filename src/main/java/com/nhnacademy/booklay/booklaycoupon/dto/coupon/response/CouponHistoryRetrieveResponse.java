package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponHistoryRetrieveResponse {
    private Long id;
    private String code;
    private String couponName;
    private String memberMemberId;
    private LocalDateTime issuedAt;
    private LocalDateTime expiredAt;
}
