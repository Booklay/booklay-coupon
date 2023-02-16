package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class MemberCouponRetrieveResponse {
    private final String name;
    private final int amount;
    private final String couponType;
    private final Long itemId;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final LocalDateTime expiredAt;
    private final Boolean isDuplicatable;
    @Setter
    private Boolean isUsed = false;
    @Setter
    private String reason = "사용 가능";

    @Builder
    public MemberCouponRetrieveResponse(String name, int amount, String couponType, Long itemId,
                                        int minimumUseAmount, int maximumDiscountAmount,
                                        LocalDateTime expiredAt, Boolean isDuplicatable,
                                        Boolean isUsed) {
        this.name = name;
        this.amount = amount;
        this.couponType = couponType;
        this.itemId = itemId;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.expiredAt = expiredAt;
        this.isDuplicatable = isDuplicatable;
        this.isUsed = isUsed;
    }

    public static  MemberCouponRetrieveResponse fromOrderCoupon(MemberOrderCouponRetrieveResponse response) {
        MemberCouponRetrieveResponse retrieveResponse = MemberCouponRetrieveResponse.builder()
            .name(response.getName())
            .amount(response.getAmount())
            .couponType(response.getCouponType())
            .itemId(response.getItemId())
            .minimumUseAmount(response.getMinimumUseAmount())
            .maximumDiscountAmount(response.getMaximumDiscountAmount())
            .expiredAt(response.getExpiredAt())
            .isDuplicatable(response.getIsDuplicatable())
            .isUsed(response.getIsUsed()).build();

        if(retrieveResponse.getIsUsed()) retrieveResponse.setReason("사용 완료");

        return retrieveResponse;
    }
}
