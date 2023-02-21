package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneCheckResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneIsBlindResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.grade.Grade;
import com.nhnacademy.booklay.booklaycoupon.entity.*;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.GetCouponService;
import com.nhnacademy.booklay.booklaycoupon.service.kafka.CouponZoneIssueService;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.NOT_REGISTERED_COUPON;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CouponZoneServiceImpl implements CouponZoneService{

    private final GetCouponZoneService couponZoneService;
    private final GetCouponService couponService;
    private final CouponZoneIssueService issueService;

    private final MemberRepository memberRepository;
    private final CouponZoneRepository couponZoneRepository;

    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    private static final String TARGET_GRADE = Grade.ANY.getKorGrade();

    /**
     * 관리자의 이달의 쿠폰 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndGradeIs(true, pageable, TARGET_GRADE);
    }

    /**
     * 관리자의 무제한 쿠폰 조회
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndGradeIs(false, pageable, TARGET_GRADE);
    }

    /**
     * 관리자의 등급별 쿠폰 조회.
     */
    @Override
    public Page<CouponZoneResponse> retrieveAdminCouponZoneGraded(Pageable pageable) {
        return couponZoneRepository.findAllByGradeIsNot(pageable, Grade.ANY.getKorGrade());
    }

    /**
     * 사용자의 이달의 쿠폰 조회.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalseAndGradeIs(true, pageable,
            TARGET_GRADE);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalseAndGradeIs(false, pageable,
            TARGET_GRADE);
    }

    @Override
    public Page<CouponZoneResponse> retrieveCouponZoneGraded(Pageable pageable) {
        return couponZoneRepository.findAllByGradeIsNotAndIsBlindIsFalse(pageable, TARGET_GRADE);
    }

    @Override
    public void createAtCouponZone(CouponZoneCreateRequest couponRequest) {
        Long couponId = couponRequest.getCouponId();

        Coupon coupon = couponService.checkCouponExist(couponId);
        ObjectFile file = coupon.getFile();

        CouponZone couponZone = CouponZoneCreateRequest.toEntity(couponRequest, coupon.getName(),
            coupon.getIsLimited(), coupon.getMaximumDiscountAmount(), couponId);

        if(Objects.nonNull(file)) {
            couponZone.setFile(file);
        }

        couponZoneRepository.save(couponZone);
    }

    @Override
    public void deleteAtCouponZone(Long couponZoneId) {
        if(!couponZoneRepository.existsById(couponZoneId)) {
            throw new NotFoundException("couponZone", couponZoneId);
        }

        couponZoneRepository.deleteById(couponZoneId);
    }

    @Override
    public CouponZoneIsBlindResponse retrieveCouponZoneIsBlind(Long couponZoneId) {
        CouponZone couponZone = couponZoneService.checkCouponExistAtZone(couponZoneId);

        return new CouponZoneIsBlindResponse(couponZone.getIsBlind());
    }

    @Override
    public void updateIsBlind(Long couponZoneId, CouponZoneIsBlindRequest request) {
        CouponZone couponZone = couponZoneService.checkCouponExistAtZone(couponZoneId);

        couponZone.setIsBlind(request.getIsBlind());
    }

    @Override
    public CouponZoneCheckResponse retrieveCouponZoneInform(Long couponId) {
        // 쿠폰존에 등록된 쿠폰인지 확인.
        CouponZone couponAtZone = couponZoneService.checkCouponExistAtZoneByCouponId(couponId);

        // isBlind = true 라면, 발급되지 않음.
        if (Boolean.TRUE.equals(couponAtZone.getIsBlind())) {
            throw new IllegalArgumentException(NOT_REGISTERED_COUPON);
        }

        return couponZoneRepository.getByCouponId(couponId);
    }

    @Override
    public String issueNoLimitCoupon(Long couponId, Long memberNo) {
        // 쿠폰존에 등록된 쿠폰인지 확인.
        CouponZone couponAtZone = couponZoneService.checkCouponExistAtZoneByCouponId(couponId);

        // isBlind = true 라면, 발급되지 않음.
        if (Boolean.TRUE.equals(couponAtZone.getIsBlind()))
            throw new IllegalArgumentException(NOT_REGISTERED_COUPON);

        // 쿠폰이 주문쿠폰에 있는지, 상품쿠폰에 있는지 확인.
        Coupon coupon = couponService.checkCouponExist(couponId);
        String orderOrProduct = couponService.isOrderOrProduct(coupon);

        // 이번 달에 받았는지 확인하고, 발급.
        if (orderOrProduct.equals("product")) {
            issueService.checkAlreadyIssuedAtProductCoupon(couponId, memberNo);
            Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new NotFoundException("member", memberNo));

            ProductCoupon productCoupon = new ProductCoupon(coupon, CodeUtils.getProductCouponCode());

            productCoupon.setIssuedAt(LocalDateTime.now());
            productCoupon.setExpiredAt(couponAtZone.getExpiredAt());
            productCoupon.setMember(member);

            productCouponRepository.save(productCoupon);
        } else {
            issueService.checkAlreadyIssuedAtOrderCoupon(couponId, memberNo);

            OrderCoupon orderCoupon = new OrderCoupon(coupon, CodeUtils.getOrderCouponCode(), false);
            orderCoupon.setMemberNo(memberNo);
            orderCoupon.setIssuedAt(LocalDateTime.now());
            orderCoupon.setExpiredAt(couponAtZone.getExpiredAt());

            orderCouponRepository.save(orderCoupon);
        }
        return "발급 완료되었습니다!";
    }
}
