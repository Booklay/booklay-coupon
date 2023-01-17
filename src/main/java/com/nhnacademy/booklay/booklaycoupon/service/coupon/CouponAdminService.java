package com.nhnacademy.booklay.booklaycoupon.service.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponAdminService {

    void createCoupon(CouponCURequest couponRequest);
    Page<CouponRetrieveResponse> retrieveAllCoupons(Pageable pageable);
    CouponDetailRetrieveResponse retrieveCoupon(Long couponId);
    void updateCoupon(Long couponId, CouponCURequest couponRequest);
    void deleteCoupon(Long couponId);
}
