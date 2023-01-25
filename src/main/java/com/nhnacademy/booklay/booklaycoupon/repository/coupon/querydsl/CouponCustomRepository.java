package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import java.util.List;

public interface CouponCustomRepository {
    List<CouponHistoryRetrieveResponse> getCouponHistoryAtOrderCoupon();
    List<CouponHistoryRetrieveResponse> getCouponHistoryAtProductCoupon();
}
