package com.nhnacademy.booklay.booklaycoupon.service.kafka;

import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.ALREADY_ISSUED;
import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.NOT_REGISTERED_COUPON;
import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.NO_STORAGE;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.message.CouponIssueRequestMessage;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.message.CouponIssueResponseMessage;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.GetCouponService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponZoneIssueService {

    @Value(value = "${message.topic.coupon.response}")
    private String responseTopic;

    private final GetCouponService couponService;
    private final CouponZoneRepository couponZoneRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;
    private final MemberRepository memberRepository;

    private final KafkaTemplate<String, CouponIssueResponseMessage> kafkaTemplate;

    @KafkaListener(topics = "${message.topic.coupon.request}", containerFactory = "kafkaListenerContainerFactory")
    public void issueCoupon(CouponIssueRequestMessage request) {
        Long couponId = request.getCouponId();
        Long memberNo = request.getMemberId();

        log.info(couponId.toString() + "번 쿠폰 발급 요청 왔음");
        try {
            // 쿠폰존에 등록된 쿠폰인지 확인.
            CouponZone couponAtZone = couponZoneRepository.findByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_REGISTERED_COUPON));

            // isBlind = true 라면, 발급되지 않음.
            if (couponAtZone.getIsBlind())
                throw new IllegalArgumentException(NOT_REGISTERED_COUPON);

            // 쿠폰이 주문쿠폰에 있는지, 상품쿠폰에 있는지 확인.
            Coupon coupon = couponService.checkCouponExist(couponId);
            String orderOrProduct = couponService.isOrderOrProduct(coupon);


            // 이미 발급 받았는지 확인 후, 재고가 남아있다면 발급.
            if (orderOrProduct.equals("product")) {
                checkAlreadyIssuedAtProductCoupon(couponId, memberNo);
                issueAtProductCoupon(couponId, memberNo, couponAtZone.getExpiredAt());
            } else {
                checkAlreadyIssuedAtOrderCoupon(couponId, memberNo);
                issueAtOrderCoupon(couponId, memberNo, couponAtZone.getExpiredAt());
            }

            responseIssueCoupon(request.getUuid(), "발급 완료되었습니다!");

        } catch (IllegalArgumentException ex){
            // 에러 메시지 전송.
            responseIssueCoupon(request.getUuid(), ex.getMessage());
        } finally {
            log.info("메세지 넣어졌음..");
        }
    }

    public void checkAlreadyIssuedAtOrderCoupon(Long couponId, Long memberNo) {
        if(orderCouponRepository.existsByCouponIdAndMemberNoIs(couponId, memberNo)) {
            throw new IllegalArgumentException(ALREADY_ISSUED);
        }
    }

    public void checkAlreadyIssuedAtProductCoupon(Long couponId, Long memberNo) {
        if(productCouponRepository.existsByCouponIdAndMemberNoIs(couponId, memberNo)) {
            throw new IllegalArgumentException(ALREADY_ISSUED);
        }
    }

    public void responseIssueCoupon(String uuid, String message) {
        CouponIssueResponseMessage response = new CouponIssueResponseMessage(uuid, message);
        kafkaTemplate.send(responseTopic, response);
    }

    public void issueAtOrderCoupon(Long couponId, Long memberNo, LocalDateTime expiredAt) {
        OrderCoupon coupon = orderCouponRepository
            .findFirstByMemberIsNullAndCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException(NO_STORAGE));

        coupon.setIssuedAt(LocalDateTime.now());
        coupon.setMemberNo(memberNo);
        coupon.setExpiredAt(expiredAt);

        orderCouponRepository.save(coupon);
    }

    public void issueAtProductCoupon(Long couponId, Long memberNo, LocalDateTime expiredAt) {
        ProductCoupon coupon = productCouponRepository
            .findFirstByMemberIsNullAndCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException(NO_STORAGE));

        Member member = memberRepository.findById(memberNo)
            .orElseThrow(() -> new NotFoundException("member", memberNo));
        coupon.setIssuedAt(LocalDateTime.now());
        coupon.setMember(member);
        coupon.setExpiredAt(expiredAt);

        productCouponRepository.save(coupon);
    }
}
