package com.nhnacademy.booklay.booklaycoupon.service.kafka;

import org.springframework.stereotype.Service;

@Service
public class CouponZoneIssueService {

    // Listener
    public void issueCoupon() {
        // TODO Coupon 유효성 검사 (쿠폰존에 등록된 쿠폰인지, 존재하는 쿠폰인지), 발급, MQ추가(완료됨.)
    }

    // kafkaTempalte
    public void responseIssueCoupon() {
        // TODO 완료됨 응답 보냄.
    }
}
