package com.nhnacademy.booklay.booklaycoupon.service.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;

public interface CouponIssueService {
    void issueCouponToMember(CouponIssueToMemberRequest couponRequest);
    void issueCoupon(CouponIssueRequest couponRequest);
}
