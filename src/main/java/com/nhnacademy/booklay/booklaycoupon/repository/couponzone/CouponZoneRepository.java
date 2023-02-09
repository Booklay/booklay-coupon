package com.nhnacademy.booklay.booklaycoupon.repository.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneIsBlindResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponZoneRepository extends JpaRepository<CouponZone, Long> {
    Page<CouponZoneResponse> findAllByIsLimitedIsAndGradeNull(Boolean isLimited, Pageable pageable);
    Page<CouponZoneResponse> findAllByGradeNotNullAndIsBlindIsFalse(Pageable pageable);
    Page<CouponZoneResponse> findAllByIsLimitedIsAndIsBlindIsFalseAndGradeNull(Boolean isLimited, Pageable pageable);
    Page<CouponZoneResponse> findAllByGradeNotNull(Pageable pageable);
    Optional<CouponZone> findByCouponId(Long couponId);
}
