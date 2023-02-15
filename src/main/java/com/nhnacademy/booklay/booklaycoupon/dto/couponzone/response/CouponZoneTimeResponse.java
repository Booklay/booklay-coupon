package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponZoneTimeResponse {
    private final LocalDateTime openedAt;
    private final LocalDateTime issuanceDeadlineAt;
}
