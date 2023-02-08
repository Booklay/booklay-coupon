package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponZoneResponse {
    private final Long id;
    private final Long couponId;
    private final Long fileId;
    private final String name;
    private final String description;
    private final String grade;
    private final LocalDateTime openedAt;
    private final LocalDateTime issuanceDeadlineAt;
    private final LocalDateTime expiredAt;
    private final Boolean isBlind;
}
