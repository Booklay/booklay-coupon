package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {
    Page<CouponRetrieveResponseFromProduct> findAllByCoupon_Product_IdAndMember_MemberNoAndCoupon_IsDuplicatable(Long productNo, Long memberNo, Boolean isDuplicatable, Pageable pageable);
    CouponRetrieveResponseFromProduct findByCode(String code);
}
