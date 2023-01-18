package com.nhnacademy.booklay.booklaycoupon.entity;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCURequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon_template")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTemplate {

    @Id
    @Column(name = "coupon_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_no")
    private Long imageNo;

    @Column(name = "code")
    private Long typeCode;

    @Column(name = "is_order_coupon")
    private Boolean isOrderCoupon;
    @Column(name = "apply_item_id")
    private Long applyItemId;

    @Column
    private String name;

    @Column
    private Integer amount;

    @Column(name = "minimum_use_amount")
    private Integer minimumUseAmount;

    @Column(name = "maximum_discount_amount")
    private Integer maximumDiscountAmount;

    @Column(name = "issuing_dead_line")
    private LocalDateTime issuingDeadLine;
    @Column(name = "validate_term")
    private Integer validateTerm;

    @Column(name = "is_duplicatable")
    private Boolean isDuplicatable;


    @Builder
    public CouponTemplate(Long imageNo, Long typeCode, Boolean isOrderCoupon,
                          Long applyItemId,
                          String name, int amount, int minimumUseAmount, int maximumDiscountAmount,
                          LocalDateTime issuingDeadLine,
                          Integer validateTerm, Boolean isDuplicatable) {
        this.imageNo = imageNo;
        this.typeCode = typeCode;
        this.isOrderCoupon = isOrderCoupon;
        this.applyItemId = applyItemId;
        this.name = name;
        this.amount = amount;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.issuingDeadLine = issuingDeadLine;
        this.validateTerm = validateTerm;
        this.isDuplicatable = isDuplicatable;
    }

    public CouponCURequest toCouponCURequest() {
        return new CouponCURequest(name, imageNo, typeCode, amount
            , isOrderCoupon, applyItemId, minimumUseAmount, maximumDiscountAmount,
            LocalDateTime.now().plusDays(validateTerm),isDuplicatable, false);
    }

    public void update(CouponTemplateCURequest couponTemplateCURequest, Long imageNo) {
        this.imageNo = imageNo;
        this.typeCode = couponTemplateCURequest.getTypeCode();
        this.isOrderCoupon = couponTemplateCURequest.getIsOrderCoupon();
        this.applyItemId = couponTemplateCURequest.getApplyItemId();
        this.name = couponTemplateCURequest.getName();
        this.amount = couponTemplateCURequest.getAmount();
        this.minimumUseAmount = couponTemplateCURequest.getMinimumUseAmount();
        this.maximumDiscountAmount = couponTemplateCURequest.getMaximumDiscountAmount();
        this.issuingDeadLine = couponTemplateCURequest.getIssuingDeadLine();
        this.validateTerm = couponTemplateCURequest.getValidateTerm();
        this.isDuplicatable = couponTemplateCURequest.getIsDuplicatable();
    }
}
