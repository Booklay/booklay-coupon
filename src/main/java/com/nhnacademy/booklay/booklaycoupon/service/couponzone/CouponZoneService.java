package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponZoneService {
    Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable);
    Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable);
    void createAtCouponZone(CouponZoneCreateRequest couponRequest);
}
