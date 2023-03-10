package com.nhnacademy.booklay.booklaycoupon.repository.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.CouponCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponCustomRepository {
    Page<CouponRetrieveResponse> findAllBy(Pageable pageable);
}
