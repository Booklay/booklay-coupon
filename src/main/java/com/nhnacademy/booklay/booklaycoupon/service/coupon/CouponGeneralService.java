package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;

public interface CouponGeneralService {
    CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode);
}
