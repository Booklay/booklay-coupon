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
    public List<CouponHistoryRetrieveResponse> getCouponHistoryAtOrderAndProduct() {
        QCoupon coupon = QCoupon.coupon;
        QProductCoupon productCoupon = QProductCoupon.productCoupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;

        return from(coupon)
            .leftJoin(productCoupon).on(coupon.id.eq(productCoupon.coupon.id))
            .leftJoin(orderCoupon).on(coupon.id.eq(orderCoupon.coupon.id))
            .where(productCoupon.code.isNotNull().or(orderCoupon.code.isNotNull()))
            .select(Projections.constructor(CouponHistoryRetrieveResponse.class,
                coupon.id,
                coupon.name))
            .fetch();
    }
}
