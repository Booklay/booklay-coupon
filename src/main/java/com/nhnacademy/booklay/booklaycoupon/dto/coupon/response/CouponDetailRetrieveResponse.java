package com.nhnacademy.booklay.booklaycoupon.dto.coupon.response;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class CouponDetailRetrieveResponse {
    private final Long id;
    private final String name;
    private final String typeName;
    private final int amount;
    @Setter
    private Long applyItemId;
    @Setter
    private String itemName;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final LocalDateTime issuanceDeadlineAt;
    private final Boolean isDuplicatable;
    private final Boolean isLimited;
    @Setter
    private Long objectFileId;
    @Setter
    private Boolean isOrderCoupon = true;
    private final int validateTerm;

    @Builder
    public CouponDetailRetrieveResponse(Long id, String name, String typeName, int amount,
                                        Long applyItemId, String itemName, int minimumUseAmount,
                                        int maximumDiscountAmount, LocalDateTime issuanceDeadlineAt,
                                        Boolean isDuplicatable, Boolean isLimited,
                                        Long objectFileId, Boolean isOrderCoupon, int validateTerm) {
        this.id = id;
        this.name = name;
        this.typeName = typeName;
        this.amount = amount;
        this.applyItemId = applyItemId;
        this.itemName = itemName;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.issuanceDeadlineAt = issuanceDeadlineAt;
        this.isDuplicatable = isDuplicatable;
        this.isLimited = isLimited;
        this.objectFileId = objectFileId;
        this.isOrderCoupon = isOrderCoupon;
        this.validateTerm = validateTerm;
    }

    public static CouponDetailRetrieveResponse fromEntity(Coupon coupon) {
        return CouponDetailRetrieveResponse.builder()
            .id(coupon.getId())
            .name(coupon.getName())
            .typeName(coupon.getCouponType().getName())
            .amount(coupon.getAmount())
            .minimumUseAmount(coupon.getMinimumUseAmount())
            .minimumUseAmount(coupon.getMaximumDiscountAmount())
            .issuanceDeadlineAt(coupon.getIssuanceDeadlineAt())
            .isDuplicatable(coupon.getIsDuplicatable())
            .isLimited(coupon.getIsLimited())
            .validateTerm(coupon.getValidateTerm())
            .build();
    }
}
