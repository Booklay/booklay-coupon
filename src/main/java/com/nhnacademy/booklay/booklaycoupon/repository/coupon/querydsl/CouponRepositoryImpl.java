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
    public List<CouponHistoryRetrieveResponse> getCouponHistoryAtOrderCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QOrderCoupon orderCoupon = QOrderCoupon.orderCoupon;
        QMember member = QMember.member;

        return from(coupon)
            .innerJoin(orderCoupon).on(coupon.id.eq(orderCoupon.coupon.id))
            .leftJoin(member).on(orderCoupon.member.memberId.eq(member.memberId))
            .select(Projections.constructor(CouponHistoryRetrieveResponse.class,
                coupon.id.as("id"),
                orderCoupon.code.as("code"),
                coupon.name.as("name"),
                orderCoupon.member.memberId.as("memberId"),
                orderCoupon.issuedAt.as("issuedAt"),
                orderCoupon.expiredAt.as("expiredAt")
                ))
            .fetch();
    }

    @Override
    public List<CouponHistoryRetrieveResponse> getCouponHistoryAtProductCoupon() {
        QCoupon coupon = QCoupon.coupon;
        QProductCoupon productCoupon = QProductCoupon.productCoupon;
        QMember member = QMember.member;

        return from(coupon)
            .innerJoin(productCoupon).on(coupon.id.eq(productCoupon.coupon.id))
            .leftJoin(member).on(productCoupon.member.memberId.eq(member.memberId))
            .select(Projections.constructor(CouponHistoryRetrieveResponse.class,
                coupon.id,
                productCoupon.code,
                coupon.name,
                productCoupon.member.memberId,
                productCoupon.issuedAt,
                productCoupon.expiredAt
                ))
            .fetch();
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
