package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponMemberServiceImpl implements CouponMemberService{

    private final CouponRepository couponRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<MemberCouponRetrieveResponse> retrieveCoupons(Long memberNo, Pageable pageable) {
        return couponRepository.getCouponsByMember(memberNo);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberNo, Pageable pageable) {
        return couponRepository.getPointCouponByMember(memberNo, pageable);
    }
}
