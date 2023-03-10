package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneIsBlindResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneCheckResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponZoneService {
    Page<CouponZoneResponse> retrieveAdminLimited(Pageable pageable);
    Page<CouponZoneResponse> retrieveAdminUnlimited(Pageable pageable);
    Page<CouponZoneResponse> retrieveAdminCouponZoneGraded(Pageable pageable);

    Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable);
    Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable);
    Page<CouponZoneResponse> retrieveCouponZoneGraded(Pageable pageable);

    void createAtCouponZone(CouponZoneCreateRequest couponRequest);
    void deleteAtCouponZone(Long couponZoneId);

    CouponZoneIsBlindResponse retrieveCouponZoneIsBlind(Long couponZoneId);
    void updateIsBlind(Long couponZoneId, CouponZoneIsBlindRequest request);

    CouponZoneCheckResponse retrieveCouponZoneInform(Long couponId);
    String issueNoLimitCoupon(Long couponId, Long memberNo);
}
