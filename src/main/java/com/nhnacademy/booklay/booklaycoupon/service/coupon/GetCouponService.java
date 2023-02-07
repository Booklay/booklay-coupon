package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCouponService {

    private final CouponRepository couponRepository;

    public Coupon checkCouponExist(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException("coupon", couponId));
    }
}
