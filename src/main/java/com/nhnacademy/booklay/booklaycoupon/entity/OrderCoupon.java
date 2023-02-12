package com.nhnacademy.booklay.booklaycoupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "order_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCoupon {

    @Id
    @Column(name = "order_coupon_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;
    @Column(name = "coupon_no", insertable = false, updatable = false)
    private Long couponNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;
    @Setter
    @Column(name = "member_no", insertable = false, updatable = false)
    private Long memberNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_no")
    private Order order;
    @Setter
    @Column(name = "order_no", insertable = false, updatable = false)
    private Long orderNo;
    @Column(name = "coupon_code")
    private String code;

    @Column(name = "issued_at")
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at")
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiredAt;

    @Column(name = "is_used")
    @Setter
    private Boolean isUsed = false;

    @Builder
    public OrderCoupon(Coupon coupon, String code, Boolean isUsed) {
        this.coupon = coupon;
        this.code = code;
        this.isUsed = isUsed;
    }
}
