package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponIssueServiceImpl implements CouponIssueService{

    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;
    private final CouponJdbcRepository couponJdbcRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    @Override
    public void issueCouponToMember(CouponIssueToMemberRequest couponRequest) {

        Long couponId = couponRequest.getCouponId();
        Long memberId = couponRequest.getMemberId();

        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException(Coupon.class.toString(), couponId));

        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException(Member.class.toString(), memberId));

        if(Objects.nonNull(coupon.getCategory())) {
            OrderCoupon orderCoupon = new OrderCoupon(coupon, getCode());
            orderCoupon.setMember(member);

            orderCouponRepository.save(orderCoupon);
        } else if (Objects.nonNull(coupon.getProduct())){
            ProductCoupon productCoupon = new ProductCoupon(coupon, getCode());
            productCoupon.setMember(member);

            productCouponRepository.save(productCoupon);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void issueCoupon(CouponIssueRequest couponRequest) {

        Long couponId = couponRequest.getCouponId();
        int quantity = couponRequest.getQuantity();

        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException(Coupon.class.toString(), couponId));

        if(Objects.nonNull(coupon.getCategory())) {
            couponJdbcRepository.saveOrderCoupons(couponId, quantity);
        } else if (Objects.nonNull(coupon.getProduct())){
            couponJdbcRepository.saveProductCoupons(couponId, quantity);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private String getCode() {
        return UUID.randomUUID().toString().substring(0, 30);
    }
}
