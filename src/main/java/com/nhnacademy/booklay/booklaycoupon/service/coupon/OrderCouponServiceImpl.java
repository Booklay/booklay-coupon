package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCouponServiceImpl implements OrderCouponService{
    private final OrderCouponRepository orderCouponRepository;
    private final CouponJdbcRepository couponJdbcRepository;
    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNo(Long memberNo, Boolean isDuplicable, Pageable pageable) {
        return orderCouponRepository.findAllByMember_MemberNoAndCoupon_IsDuplicatableAndCoupon_CategoryNoNotNullAndIsUsedAndExpiredAtAfter(memberNo, isDuplicable,false, LocalDateTime.now(), pageable);
    }
    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return orderCouponRepository.findByCode(couponCode);
    }

    @Override
    public List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
            List<String> couponCodeList, Long memberNo) {
        return orderCouponRepository.findAllByCodeInAndMemberNoAndOrderNoIsNull(couponCodeList, memberNo);
    }

    @Override
    public void usingCoupon(List<CouponUsingDto> categoryCouponList) {
        if (categoryCouponList!=null){
            List<Long> usedCouponNoList = categoryCouponList.stream().map(CouponUsingDto::getSpecifiedCouponNo).collect(Collectors.toList());
            couponJdbcRepository.useOrderCoupons(usedCouponNoList, categoryCouponList.get(0).getUsedTargetNo());
        }
    }

    @Override
    public void refundCoupon(Long orderNo) {
        couponJdbcRepository.refundOrderCoupons(orderNo);
    }

}
