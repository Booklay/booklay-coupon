package com.nhnacademy.booklay.booklaycoupon.service;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author 오준후
 */
@Service
@RequiredArgsConstructor
public class CouponComplexServiceImpl implements CouponComplexService {

    private final CouponAdminService couponAdminService;
    private final CouponIssueService couponIssueService;

    @Override
    public void createAndIssueCouponByTemplate(CouponTemplate couponTemplate, Long memberId){
        Long couponId = couponAdminService.createCoupon(couponTemplate.toCouponCURequest()).getId();
        couponIssueService.issueCouponToMember(new CouponIssueToMemberRequest(couponId, memberId));
    }
}
