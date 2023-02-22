package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;

import java.util.List;

public interface CouponGeneralService {
    CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode);

    List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
            List<String> couponCodeList, Long memberNo);

    void couponUsing(CouponUseRequest couponUseRequest);

    void couponRefund(CouponRefundRequest couponRefundRequest);
}
