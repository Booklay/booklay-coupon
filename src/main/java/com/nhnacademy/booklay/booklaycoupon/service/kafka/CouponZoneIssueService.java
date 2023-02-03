package com.nhnacademy.booklay.booklaycoupon.service.kafka;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.message.CouponIssueRequestMessage;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.message.CouponIssueResponseMessage;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.GetCouponService;
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

    private final KafkaTemplate<String, CouponIssueResponseMessage> kafkaTemplate;

    @KafkaListener(topics = "${message.topic.coupon.request}", groupId = "coupon", containerFactory = "kafkaListenerContainerFactory")
    public void issueCoupon(CouponIssueRequestMessage request) {
        // TODO Coupon 유효성 검사 (쿠폰존에 등록된 쿠폰인지, 존재하는 쿠폰인지), 발급, MQ추가(완료됨.)

        Long couponId = request.getCouponId();
        Long memberId = request.getMemberId();

        // 존재하는 쿠폰인지 확인
        Coupon targetCoupon = couponService.checkCouponExist(couponId);
        log.info("issueCoupon : " + couponId.toString());

        // 쿠폰존에 등록된 쿠폰인지 확인.
        if(!couponZoneRepository.existsByCouponId(couponId)) throw new NotFoundException("couponId", couponId);

        // 발급
        

        // 완료 메시지 보냄.
        responseIssueCoupon(couponId, request.getMemberId());
    }

    public void responseIssueCoupon(Long couponId, Long memberId) {
        CouponIssueResponseMessage response = new CouponIssueResponseMessage(couponId, memberId, "success");
        kafkaTemplate.send(responseTopic, response);
    }
}
