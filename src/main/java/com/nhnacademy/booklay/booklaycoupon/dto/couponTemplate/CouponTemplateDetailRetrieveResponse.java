package com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CouponTemplateDetailRetrieveResponse {
    private final Long id;
    private final String imagePath;
    private final Long typeCode;
    private final String name;
    private final Boolean isOrderCoupon;
    private final Long applyItemId;
    private final int amount;
    private final int minimumUseAmount;
    private final int maximumDiscountAmount;
    private final LocalDateTime issuanceDeadlineAt;
    private final Integer validateTerm;
    private final Boolean isDuplicatable;

    @Builder
    public CouponTemplateDetailRetrieveResponse(Long id, String imagePath, Long typeCode, String name,
                                                Boolean isOrderCoupon, Long applyItemId, int amount,
                                                int minimumUseAmount, int maximumDiscountAmount,
                                                LocalDateTime issuanceDeadlineAt,
                                                Integer validateTerm, Boolean isDuplicatable) {
        this.id = id;
        this.imagePath = imagePath;
        this.typeCode = typeCode;
        this.name = name;
        this.isOrderCoupon = isOrderCoupon;
        this.applyItemId = applyItemId;
        this.amount = amount;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.issuanceDeadlineAt = issuanceDeadlineAt;
        this.validateTerm = validateTerm;
        this.isDuplicatable = isDuplicatable;
    }



    public static CouponTemplateDetailRetrieveResponse fromEntity(CouponTemplate couponTemplate) {
        return CouponTemplateDetailRetrieveResponse.builder()
            .id(couponTemplate.getId())
            .imagePath(couponTemplate.getImage().getAddress())
            .typeCode(couponTemplate.getTypeCode())
            .name(couponTemplate.getName())
            .isOrderCoupon(couponTemplate.getIsOrderCoupon())
            .applyItemId(couponTemplate.getApplyItemId())
            .amount(couponTemplate.getAmount())
            .minimumUseAmount(couponTemplate.getMinimumUseAmount())
            .maximumDiscountAmount(couponTemplate.getMaximumDiscountAmount())
            .issuanceDeadlineAt(couponTemplate.getIssuingDeadLine())
            .isDuplicatable(couponTemplate.getIsDuplicatable())
            .validateTerm(couponTemplate.getValidateTerm())
            .build();
    }
}
