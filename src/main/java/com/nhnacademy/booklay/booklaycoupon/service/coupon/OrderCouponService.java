package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import java.util.Collection;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCouponService {
    Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNo(Long memberNo, Boolean isDuplicable, Pageable pageable);

    CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode);

    List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(List<String> couponCodeList);

    void usingCoupon(List<CouponUsingDto> categoryCouponList);

    void refundCoupon(Long orderNo);
}
