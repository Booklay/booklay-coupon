package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
    Page<CouponTemplateRetrieveResponse> findAllBy(Pageable pageable);
}
