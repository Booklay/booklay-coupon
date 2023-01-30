package com.nhnacademy.booklay.booklaycoupon.entity;

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

@Table(name = "coupon_zone")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponZone {

    @Id
    @Column(name = "coupon_zone_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "short_description")
    private String description;

    @Column(name = "opened_at")
    private LocalDateTime openedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "maximum_discount_amount")
    private int maximumDiscountAmount;

    @Column(name = "coupon_no")
    private Long couponId;

    @Column(name = "is_blind")
    private Boolean isBlind;

    @Column(name = "is_limited")
    private Boolean isLimited;

    @Builder
    public CouponZone(String name, String description, LocalDateTime openedAt,
                      LocalDateTime closedAt,
                      int maximumDiscountAmount, Long couponId, Boolean isBlind,
                      Boolean isLimited) {
        this.name = name;
        this.description = description;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.maximumDiscountAmount = maximumDiscountAmount;
        this.couponId = couponId;
        this.isBlind = isBlind;
        this.isLimited = isLimited;
    }
}
