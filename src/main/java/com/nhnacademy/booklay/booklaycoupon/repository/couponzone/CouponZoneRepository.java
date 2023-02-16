package com.nhnacademy.booklay.booklaycoupon.repository.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneCheckResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponZoneRepository extends JpaRepository<CouponZone, Long> {
    Page<CouponZoneResponse> findAllByIsLimitedIsAndGradeIs(Boolean isLimited, Pageable pageable, String grade);
    Page<CouponZoneResponse> findAllByGradeIsNot(Pageable pageable, String grade);

    Page<CouponZoneResponse> findAllByIsLimitedIsAndIsBlindIsFalseAndGradeIs(Boolean isLimited, Pageable pageable, String grade);
    Page<CouponZoneResponse> findAllByGradeIsNotAndIsBlindIsFalse(Pageable pageable, String grade);

    Optional<CouponZone> findByCouponId(Long couponId);
    CouponZoneCheckResponse getByCouponId(Long couponId);
}
