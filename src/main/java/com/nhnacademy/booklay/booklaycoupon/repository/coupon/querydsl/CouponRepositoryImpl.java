package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderCoupon;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class CouponRepositoryImpl extends QuerydslRepositorySupport implements CouponCustomRepository {

    public CouponRepositoryImpl() {
        super(Coupon.class);
    }

    @Override
    public Page<PointCouponRetrieveResponse> getPointCouponByMember(Long memberNo, Pageable pageable) {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;

        QueryResults<PointCouponRetrieveResponse> list = from(orderCoupon)
            .where(orderCoupon.member.memberNo.eq(memberNo))
            .where(orderCoupon.coupon.couponType.name.eq("포인트"))
            .where(orderCoupon.isUsed.eq(false))
            .where(orderCoupon.expiredAt.before(LocalDateTime.now()))
            .select(Projections.constructor(PointCouponRetrieveResponse.class,
                coupon.id,
                orderCoupon.id,
                coupon.name,
                coupon.amount))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(list.getResults(), pageable, list.getTotal());
    }
}
