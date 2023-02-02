package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {
    Page<CouponRetrieveResponseFromProduct> findAllByCoupon_Product_IdAndMember_MemberNo(Long productNo, Long memberNo, Pageable pageable);
}
