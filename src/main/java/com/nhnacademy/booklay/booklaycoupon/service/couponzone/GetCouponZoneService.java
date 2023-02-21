package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.NOT_REGISTERED_COUPON;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCouponZoneService {
    private final CouponZoneRepository couponZoneRepository;

    public CouponZone checkCouponExistAtZone(Long couponZoneId) {
        return couponZoneRepository.findById(couponZoneId)
            .orElseThrow(() -> new NotFoundException("couponZone", couponZoneId));
    }

    public CouponZone checkCouponExistAtZoneByCouponId(Long couponId) {
        return couponZoneRepository.findByCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException(NOT_REGISTERED_COUPON));
    }
}
