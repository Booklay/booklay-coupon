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

@Table(name = "product_coupon")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCoupon {

    @Id
    @Column(name = "product_coupon_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_no")
    private Coupon coupon;
    @Column(name = "coupon_no", insertable = false, updatable = false)
    private Long couponNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    @Setter
    private Member member;
    @Setter
    @Column(name = "member_no", insertable = false, updatable = false)
    private Long memberNo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_no", insertable = false, updatable = false)
    private OrderProduct orderProduct;
    @Setter
    @Column(name = "order_product_no")
    private Long orderProductNo;
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

    @Builder
    public ProductCoupon(Coupon coupon, String code) {
        this.coupon = coupon;
        this.code = code;
    }
}
