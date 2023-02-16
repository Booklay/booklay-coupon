package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponIssueServiceImpl implements CouponIssueService{

    private final CouponJdbcRepository couponJdbcRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;
    private final MemberRepository memberRepository;

    private final GetCouponService getCouponService;

    private static final Long POINT_COUPON_CODE = 3L;

    /**
     * 사용자에게 쿠폰을 발급합니다.
     * 포인트 쿠폰은 주문쿠폰에 저장됩니다.
     */
    @Override
    public void issueCouponToMember(CouponIssueToMemberRequest request) {

        Long couponId = request.getCouponId();
        Long memberNo = request.getMemberId();

        Coupon coupon = getCouponService.checkCouponExist(couponId);
        Member member = memberRepository.findById(memberNo)
            .orElseThrow(() -> new NotFoundException("member", memberNo));

        if(Objects.nonNull(coupon.getCategory()) || Objects.equals(coupon.getCouponType().getId(), POINT_COUPON_CODE)) {
            OrderCoupon orderCoupon = new OrderCoupon(coupon, CodeUtils.getOrderCouponCode(),false);
            orderCoupon.setMemberNo(memberNo);
            orderCoupon.setIssuedAt(LocalDateTime.now());
            orderCoupon.setExpiredAt(request.getExpiredAt());

            orderCouponRepository.save(orderCoupon);
        } else if (Objects.nonNull(coupon.getProduct())){
            ProductCoupon productCoupon = new ProductCoupon(coupon, CodeUtils.getProductCouponCode());
            productCoupon.setMember(member);
            productCoupon.setIssuedAt(LocalDateTime.now());
            productCoupon.setExpiredAt(request.getExpiredAt());

            productCouponRepository.save(productCoupon);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void issueCoupon(CouponIssueRequest couponRequest) {

        Long couponId = couponRequest.getCouponId();
        int quantity = couponRequest.getQuantity();

        Coupon coupon = getCouponService.checkCouponExist(couponId);

        if(Objects.nonNull(coupon.getCategory()) || Objects.equals(coupon.getCouponType().getId(), POINT_COUPON_CODE)) {
            couponJdbcRepository.saveOrderCoupons(couponId, quantity);
        } else if (Objects.nonNull(coupon.getProduct())){
            couponJdbcRepository.saveProductCoupons(couponId, quantity);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
