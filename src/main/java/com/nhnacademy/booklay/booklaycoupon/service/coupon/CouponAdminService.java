package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponAdminService {

    Coupon createCoupon(CouponCURequest couponRequest);
    Page<CouponRetrieveResponse> retrieveAllCoupons(Pageable pageable);
    CouponDetailRetrieveResponse retrieveCoupon(Long couponId);
    void updateCoupon(Long couponId, CouponCURequest couponRequest);
    void updateCouponImage(Long couponId, Long objectFileId);
    void deleteCouponImage(Long couponId);
    void deleteCoupon(Long couponId);
    Page<CouponHistoryRetrieveResponse> retrieveIssuedCoupons(Pageable pageable);

    Page<CouponUsedHistoryResponse> retrieveUsedCoupon(Pageable pageable);
}
