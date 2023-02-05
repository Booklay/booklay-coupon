package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCouponServiceImpl implements ProductCouponService{
    private final ProductCouponRepository productCouponRepository;

    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Boolean isDuplicatable, Pageable pageable){
        return productCouponRepository.findAllByCoupon_ProductNoAndMemberNoAndCoupon_IsDuplicatable(productNo, memberNo, isDuplicatable, pageable);
    }

    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return productCouponRepository.findByCode(couponCode);
    }
}
