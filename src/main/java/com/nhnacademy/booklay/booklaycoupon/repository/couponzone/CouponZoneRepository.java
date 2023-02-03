package com.nhnacademy.booklay.booklaycoupon.repository.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponZoneRepository extends JpaRepository<CouponZone, Long> {
    Page<CouponZoneResponse> findAllByIsLimitedIs(Boolean isLimited, Pageable pageable);
    boolean existsByCouponId(Long couponId);
}
