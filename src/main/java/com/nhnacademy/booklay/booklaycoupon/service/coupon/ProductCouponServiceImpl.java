package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductCouponServiceImpl implements ProductCouponService{
    private final ProductCouponRepository productCouponRepository;
    private final CouponJdbcRepository couponJdbcRepository;
    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Boolean isDuplicatable, Pageable pageable){
        return productCouponRepository.findAllByCoupon_ProductNoAndMember_MemberNoAndCoupon_IsDuplicatableAndOrderProductNoIsNullAndExpiredAtAfter(productNo, memberNo, isDuplicatable, LocalDateTime.now(), pageable);
    }

    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return productCouponRepository.findByCode(couponCode);
    }

    @Override
    public List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
            List<String> couponCodeList, Long memberNo) {
        return productCouponRepository.findAllByCodeInAndMemberNoAndOrderProductNoIsNull(couponCodeList, memberNo);
    }

    @Override
    public void usingCoupon(List<CouponUsingDto> productCouponList) {
        if(productCouponList!= null){
            couponJdbcRepository.useProductCoupons(productCouponList);
        }
    }

    @Override
    public void refundCoupon(List<Long> orderProductNoList) {
        couponJdbcRepository.refundProductCoupons(orderProductNoList);
    }

}
