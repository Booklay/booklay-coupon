package com.nhnacademy.booklay.booklaycoupon.repository.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.response.CouponTypeRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponTypeRepository extends JpaRepository<CouponType, Long> {
    Page<CouponTypeRetrieveResponse> findAllBy(Pageable pageable);
}
