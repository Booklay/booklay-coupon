package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class MemberCouponRetrieveResponse {
    private final String name;
    private final int amount;
    private final String couponType;
    private final Long usedItemNo;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final LocalDateTime expiredAt;
    private final Boolean isDuplicatable;
    @Setter
    private Boolean isUsed;
}
