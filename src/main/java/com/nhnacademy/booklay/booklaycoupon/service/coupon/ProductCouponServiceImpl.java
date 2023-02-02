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
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Pageable pageable){
        return productCouponRepository.findAllByCoupon_Product_IdAndMember_MemberNo(productNo, memberNo, pageable);
    }
}
