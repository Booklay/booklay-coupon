package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponMemberService {
    Page<MemberCouponRetrieveResponse> retrieveCoupons(Long memberId, Pageable pageable);
    Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberId, Pageable pageable);
}
