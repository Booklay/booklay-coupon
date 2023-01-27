package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponZoneServiceImpl implements CouponZoneService{

    private final CouponZoneRepository couponZoneRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZone(Pageable pageable) {
        return couponZoneRepository.findAllBy(pageable);
    }
}
