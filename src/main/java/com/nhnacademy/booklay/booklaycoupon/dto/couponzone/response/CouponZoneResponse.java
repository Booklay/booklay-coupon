package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CouponZoneResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime openedAt;
    private final LocalDateTime closedAt;
    private final Boolean isBlind;
}