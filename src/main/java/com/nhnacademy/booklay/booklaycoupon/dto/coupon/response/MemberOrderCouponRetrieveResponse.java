package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class MemberOrderCouponRetrieveResponse {
    private final String name;
    private final int amount;
    private final String couponType;
    private final Long itemId;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final LocalDateTime expiredAt;
    private final Boolean isDuplicatable;
    private final Boolean isUsed;
    @Setter
    private String reason = "사용 가능";
}
