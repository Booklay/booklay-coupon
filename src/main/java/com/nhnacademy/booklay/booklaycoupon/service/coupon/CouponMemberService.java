package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponMemberService {
    Page<MemberCouponRetrieveResponse> retrieveCoupons(Long memberNo, Pageable pageable);
    Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberNo, Pageable pageable);

    void retrieveCouponCount(Long memberNo);
}
