package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QMember;
import com.nhnacademy.booklay.booklaycoupon.entity.QOrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.QProductCoupon;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import java.util.List;
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
            .select(Projections.constructor(PointCouponRetrieveResponse.class,
                coupon.id,
                coupon.name,
                coupon.amount))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetchResults();

        return new PageImpl<>(list.getResults(), pageable, list.getTotal());
    }

    @Override
    public Page<MemberCouponRetrieveResponse> getCouponsByMember(Long memberId) {
        return null;
    }
}
