package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCouponServiceImpl implements OrderCouponService{
    private final OrderCouponRepository orderCouponRepository;
    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNo(Long memberNo, Boolean isDuplicable, Pageable pageable) {
        return orderCouponRepository.findAllByMember_MemberNoAndCoupon_IsDuplicatable(memberNo, isDuplicable, pageable);
    }
}
