package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductCouponService {
    Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Boolean isDuplicatable, Pageable pageable);

    CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode);

    List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(List<String> couponCodeList, Long memberNo);

    void usingCoupon(List<CouponUsingDto> productCouponList, Long memberNo);

    void refundCoupon(List<Long> orderProductNoList, Long memberNo);
}
