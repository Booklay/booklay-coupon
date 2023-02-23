package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberOrderCouponRetrieveResponse;

import java.util.List;

public interface OrderCouponCustomRepository {
    List<MemberOrderCouponRetrieveResponse> getCouponsByMember(Long memberNo);
    List<CouponUsedHistoryResponse> getUsedOrderCoupon();
}
