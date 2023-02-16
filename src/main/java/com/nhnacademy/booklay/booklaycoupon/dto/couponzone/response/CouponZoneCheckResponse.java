package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponZoneCheckResponse {
    private final LocalDateTime openedAt;
    private final LocalDateTime issuanceDeadlineAt;
    private final String grade;
}
