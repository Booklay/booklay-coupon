package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CouponZoneServiceImpl implements CouponZoneService{

    private final CouponRepository couponRepository;
    private final CouponZoneRepository couponZoneRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIs(true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIs(false, pageable);
    }

    @Override
    public void createAtCouponZone(CouponZoneCreateRequest couponRequest) {
        Long couponId = couponRequest.getCouponId();

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("Coupon", couponId));

        CouponZone couponZone = CouponZoneCreateRequest.toEntity(couponRequest, coupon.getName(),
            coupon.getIsLimited(), coupon.getMaximumDiscountAmount(), couponId);

        couponZoneRepository.save(couponZone);
    }
}