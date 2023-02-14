package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductCouponService {
    Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Boolean isDuplicatable, Pageable pageable);

    CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode);

    List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(List<String> couponCodeList);

    void usingCoupon(List<CouponUsingDto> productCouponList);

    void refundCoupon(List<Long> orderProductNoList);
}
