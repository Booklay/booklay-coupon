package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponUsedHistoryResponse {
    private final String memberId;
    private final String couponName;
    private final Long discountPrice;
    private final LocalDateTime orderedAt;
    private final LocalDateTime issuedAt;
}
