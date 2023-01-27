package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponZoneService {
    Page<CouponZoneResponse> retrieveCouponZone(Pageable pageable);
}
