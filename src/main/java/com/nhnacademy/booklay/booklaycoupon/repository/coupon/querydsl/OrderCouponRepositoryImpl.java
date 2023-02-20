package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberOrderCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.*;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class OrderCouponRepositoryImpl extends QuerydslRepositorySupport implements
    OrderCouponCustomRepository {

    public OrderCouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public List<MemberOrderCouponRetrieveResponse> getCouponsByMember(Long memberNo) {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;
        QMember member = QMember.member;
        QCouponType type = QCouponType.couponType;

        return from(orderCoupon)
            .where(orderCoupon.member.memberNo.eq(memberNo))
            .innerJoin(orderCoupon.coupon, coupon)
            .leftJoin(type).on(coupon.couponType.id.eq(type.id))
            .innerJoin(orderCoupon.member, member)
            .select(Projections.constructor(MemberOrderCouponRetrieveResponse.class,
                coupon.name,
                coupon.amount,
                type.name,
                orderCoupon.orderNo,
                coupon.minimumUseAmount,
                coupon.maximumDiscountAmount,
                orderCoupon.expiredAt,
                coupon.isDuplicatable,
                orderCoupon.isUsed))
            .fetch();
    }

    @Override
    public List<CouponUsedHistoryResponse> getUsedOrderCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;
        QMember member = QMember.member;
        QOrder order = QOrder.order;

        return from(orderCoupon)
            .where(orderCoupon.orderNo.isNotNull())
            .leftJoin(coupon).on(orderCoupon.coupon.id.eq(coupon.id))
            .leftJoin(member).on(orderCoupon.member.memberNo.eq(member.memberNo))
            .leftJoin(order).on(orderCoupon.orderNo.eq(order.id))
            .select(Projections.constructor(CouponUsedHistoryResponse.class,
                member.memberId,
                coupon.name,
                order.discountPrice,
                order.orderedAt,
                orderCoupon.issuedAt))
            .fetch();
    }
}
