package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponCustomRepository {
    Page<PointCouponRetrieveResponse> getPointCouponByMember(Long memberNo, Pageable pageable);
}
