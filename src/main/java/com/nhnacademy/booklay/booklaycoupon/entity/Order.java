package com.nhnacademy.booklay.booklaycoupon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "order")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @Column(name = "order_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    @OneToOne
    @JoinColumn(name = "code")
    private OrderStatusCode orderStatusCode;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Column(name = "product_price_sum")
    private Long productPriceSum;

    @Column(name = "delivery_price")
    private Long deliveryPrice;

    @Column(name = "discount_price")
    private Long discountPrice;

    @Column(name = "point_use_price")
    private Long pointUsePrice;

    @Column(name = "payment_price")
    private Long paymentPrice;

    @Column(name = "payment_method")
    private Long paymentMethod;

    @Column(name = "gift_wrapping_price")
    private Long giftWrappingPrice;

    @Column(name = "is_blinded")
    private Boolean isBlinded;

    @Builder
    public Order(Member member, OrderStatusCode orderStatusCode, Long productPriceSum, Long deliveryPrice, Long discountPrice, Long pointUsePrice, Long paymentPrice, Long paymentMethod, Long giftWrappingPrice, Boolean isBlinded) {
        this.member = member;
        this.orderStatusCode = orderStatusCode;
        this.productPriceSum = productPriceSum;
        this.deliveryPrice = deliveryPrice;
        this.discountPrice = discountPrice;
        this.pointUsePrice = pointUsePrice;
        this.paymentPrice = paymentPrice;
        this.paymentMethod = paymentMethod;
        this.giftWrappingPrice = giftWrappingPrice;
        this.isBlinded = isBlinded;
    }
}
