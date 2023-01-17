package com.nhnacademy.booklay.booklaycoupon.entity;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @Column(name = "coupon_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "image_no")
    private Image image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code")
    private CouponType couponType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no")
    @Setter
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    @Setter
    private Category category;

    @Column
    private String name;

    @Column
    private int amount;

    @Column(name = "minimum_use_amount")
    private int minimumUseAmount;

    @Column(name = "maximum_discount_amount")
    private int maximumDiscountAmount;

    @Column(name = "issuance_deadline_at")
    private LocalDateTime issuanceDeadlineAt;

    @Column(name = "is_duplicatable")
    private Boolean isDuplicatable;

    @Column(name = "is_limited")
    @Setter
    private Boolean isLimited;

    @Builder
    public Coupon(Image image, CouponType couponType, String name, int amount, int minimumUseAmount,
                  int maximumDiscountAmount, LocalDateTime issuanceDeadlineAt,
                  Boolean isDuplicatable, Boolean isLimited) {
        this.image = image;
        this.couponType = couponType;
        this.name = name;
        this.amount = amount;
        this.minimumUseAmount = minimumUseAmount;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.issuanceDeadlineAt = issuanceDeadlineAt;
        this.isDuplicatable = isDuplicatable;
        this.isLimited = isLimited;
    }

    public void update(CouponCURequest couponRequest, CouponType couponType) {
        this.name = couponRequest.getName();
        this.couponType = couponType;
        this.amount = couponRequest.getAmount();
        this.minimumUseAmount = couponRequest.getMinimumUseAmount();
        this.maximumDiscountAmount = couponRequest.getMaximumDiscountAmount();
        this.issuanceDeadlineAt = couponRequest.getIssuanceDeadlineAt();
        this.isDuplicatable = couponRequest.getIsDuplicatable();
        this.isLimited = couponRequest.getIsLimited();
    }

}
