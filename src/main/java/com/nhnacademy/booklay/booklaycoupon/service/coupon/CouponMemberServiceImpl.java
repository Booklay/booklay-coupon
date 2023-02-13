package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponMemberServiceImpl implements CouponMemberService{

    private final CouponRepository couponRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<MemberCouponRetrieveResponse> retrieveCoupons(Long memberNo, Pageable pageable) {
        List<MemberCouponRetrieveResponse> couponList = new ArrayList<>();

        List<MemberCouponRetrieveResponse> orderCouponList = orderCouponRepository.getCouponsByMember(memberNo);
        List<MemberCouponRetrieveResponse> productCouponList = productCouponRepository.getCouponsByMember(memberNo);

        couponList.addAll(orderCouponList);
        couponList.addAll(productCouponList);

        return getPage(pageable, couponList);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberNo, Pageable pageable) {
        return couponRepository.getPointCouponByMember(memberNo, pageable);
    }

    private Page<MemberCouponRetrieveResponse> getPage(Pageable pageable, List<MemberCouponRetrieveResponse> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
