package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.QMember;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrder;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.QProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.QProductCoupon;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class ProductCouponRepositoryImpl extends QuerydslRepositorySupport implements ProductCouponCustomRepository {

    public ProductCouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public List<MemberCouponRetrieveResponse> getCouponsByMember(Long memberNo) {
        QCoupon coupon = QCoupon.coupon;
        QProductCoupon productCoupon = QProductCoupon.productCoupon;
        QMember member = QMember.member;
        QCouponType type = QCouponType.couponType;

        return from(productCoupon)
            .where(productCoupon.member.memberNo.eq(memberNo))
            .leftJoin(coupon).on(productCoupon.coupon.id.eq(coupon.id))
            .leftJoin(type).on(coupon.couponType.id.eq(type.id))
            .leftJoin(member).on(productCoupon.member.memberNo.eq(member.memberNo))
            .select(Projections.constructor(MemberCouponRetrieveResponse.class,
                coupon.name,
                coupon.amount,
                type.name,
                productCoupon.orderProductNo,
                coupon.minimumUseAmount,
                coupon.maximumDiscountAmount,
                productCoupon.expiredAt,
                coupon.isDuplicatable))
            .fetch();
    }

    @Override
    public List<CouponUsedHistoryResponse> getUsedProductCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QProductCoupon productCoupon = QProductCoupon.productCoupon;
        QMember member = QMember.member;
        QOrder order = QOrder.order;
        QOrderProduct orderProduct = QOrderProduct.orderProduct;

        return from(productCoupon)
            .where(productCoupon.orderProductNo.isNotNull())
            .leftJoin(coupon).on(productCoupon.coupon.id.eq(coupon.id))
            .leftJoin(member).on(productCoupon.member.memberNo.eq(member.memberNo))
            .leftJoin(orderProduct).on(productCoupon.orderProduct.id.eq(orderProduct.id))
            .leftJoin(order).on(orderProduct.order.id.eq(order.id))
            .select(Projections.constructor(CouponUsedHistoryResponse.class,
                member.memberId,
                coupon.name,
                order.discountPrice,
                order.orderedAt,
                productCoupon.issuedAt))
            .fetch();
    }
}
