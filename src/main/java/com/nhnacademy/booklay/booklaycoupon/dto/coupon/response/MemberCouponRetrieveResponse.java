package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCouponRetrieveResponse {
    private String name;
    private int amount;
    private int minimumUseAmount;
    private int maximumDiscountAmount;
    private LocalDateTime expiredAt;
    private Boolean isDuplicatable;
}
