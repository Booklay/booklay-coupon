package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QProductCoupon;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponCustomRepository {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public List<CouponHistoryRetrieveResponse> getCouponHistoryAtOrderCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;

        return from(coupon)
            .rightJoin(orderCoupon).on(coupon.id.eq(orderCoupon.coupon.id))
            .select(Projections.constructor(CouponHistoryRetrieveResponse.class,
                coupon.id.as("id"),
                orderCoupon.code.as("code"),
                coupon.name.as("name"),
                orderCoupon.member.memberId.as("memberId"),
                orderCoupon.issuedAt.as("issuedAt")
                ))
            .fetch();
    }

    @Override
    public List<CouponHistoryRetrieveResponse> getCouponHistoryAtProductCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QProductCoupon productCoupon = QProductCoupon.productCoupon;

        return from(coupon)
            .leftJoin(productCoupon).on(coupon.id.eq(productCoupon.coupon.id))
            .where(productCoupon.code.isNotNull())
            .select(Projections.constructor(CouponHistoryRetrieveResponse.class,
                coupon.id,
                productCoupon.code,
                coupon.name,
                productCoupon.member.memberId,
                productCoupon.issuedAt
                ))
            .fetch();
    }
}
