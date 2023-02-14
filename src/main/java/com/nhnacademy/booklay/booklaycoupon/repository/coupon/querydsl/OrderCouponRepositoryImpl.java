package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.QMember;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QProductCoupon;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class OrderCouponRepositoryImpl extends QuerydslRepositorySupport implements
    OrderCouponCustomRepository {

    public OrderCouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public List<MemberCouponRetrieveResponse> getCouponsByMember(Long memberNo) {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;
        QMember member = QMember.member;
        QCouponType type = QCouponType.couponType;

        return from(orderCoupon)
            .where(orderCoupon.member.memberNo.eq(memberNo))
            .innerJoin(orderCoupon.coupon, coupon)
            .leftJoin(type).on(coupon.couponType.id.eq(type.id))
            .innerJoin(orderCoupon.member, member)
            .select(Projections.constructor(MemberCouponRetrieveResponse.class,
                coupon.name,
                coupon.amount,
                type.name,
                orderCoupon.orderNo,
                coupon.minimumUseAmount,
                coupon.maximumDiscountAmount,
                orderCoupon.expiredAt,
                coupon.isDuplicatable))
            .fetch();
    }
}
