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
import java.time.YearMonth;
import java.util.List;
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

        log.info(couponId.toString() + "??? ?????? ?????? ?????? ??????");
        try {
            // ???????????? ????????? ???????????? ??????.
            CouponZone couponAtZone = couponZoneRepository.findByCouponId(couponId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_REGISTERED_COUPON));

            // isBlind = true ??????, ???????????? ??????.
            if (Boolean.TRUE.equals(couponAtZone.getIsBlind()))
                throw new IllegalArgumentException(NOT_REGISTERED_COUPON);

            // ????????? ??????????????? ?????????, ??????????????? ????????? ??????.
            Coupon coupon = couponService.checkCouponExist(couponId);
            String orderOrProduct = couponService.isOrderOrProduct(coupon);


            // ?????? ?????? ???????????? ?????? ???, ????????? ??????????????? ??????.
            if (orderOrProduct.equals("product")) {
                checkAlreadyIssuedAtProductCoupon(couponId, memberNo);
                issueAtProductCoupon(couponId, memberNo, couponAtZone.getExpiredAt());
            } else {
                checkAlreadyIssuedAtOrderCoupon(couponId, memberNo);
                issueAtOrderCoupon(couponId, memberNo, couponAtZone.getExpiredAt());
            }

            responseIssueCoupon(request.getUuid(), "?????? ?????????????????????!");

        } catch (IllegalArgumentException ex){
            responseIssueCoupon(request.getUuid(), ex.getMessage());
        } finally {
            log.info("????????? ????????????..");
        }
    }

    /**
     * ???????????? ???????????????, ???????????? ?????? ????????? ?????? ???????????? ???????????????.
     */
    public void checkAlreadyIssuedAtOrderCoupon(Long couponId, Long memberNo) {
        if(orderCouponRepository.existsByCouponIdAndMemberNoIs(couponId, memberNo)) {
            YearMonth nowTime = YearMonth.now();

            List<OrderCoupon> couponList =
                orderCouponRepository.findAllByCouponIdAndMemberNoIs(couponId, memberNo);

            couponList.forEach(
                c -> {
                    LocalDateTime target = c.getIssuedAt();
                    YearMonth targetTime = YearMonth.of(target.getYear(), target.getMonth());
                    log.debug("???????????? ?????? ????????? ?????? ??? : {} ** ???????????? ??????????????? ??? {}", targetTime, nowTime);

                    if (nowTime.isAfter(targetTime)) {
                        return;
                    } else {
                        throw new IllegalArgumentException(ALREADY_ISSUED);
                    }
                }
            );
        }
    }

    /**
     * ???????????? ???????????????, ???????????? ?????? ????????? ?????? ???????????? ???????????????.
     * ?????? ?????? ?????? ???????????? ????????????.
     */
    public void checkAlreadyIssuedAtProductCoupon(Long couponId, Long memberNo) {
        if(productCouponRepository.existsByCouponIdAndMemberNoIs(couponId, memberNo)) {
            YearMonth nowTime = YearMonth.now();

            List<ProductCoupon> couponList =
                productCouponRepository.findAllByCouponIdAndMemberNoIs(couponId, memberNo);

            couponList.forEach(
                c -> {
                    LocalDateTime target = c.getIssuedAt();
                    YearMonth targetTime = YearMonth.of(target.getYear(), target.getMonth());
                    log.debug("???????????? ?????? ????????? ?????? ??? : {} ** ???????????? ??????????????? ??? {}", targetTime, nowTime);

                    if (nowTime.isAfter(targetTime)) {
                        return;
                    } else {
                        throw new IllegalArgumentException(ALREADY_ISSUED);
                    }
                }
            );
        }
    }

    public void responseIssueCoupon(String uuid, String message) {
        CouponIssueResponseMessage response = new CouponIssueResponseMessage(uuid, message);
        kafkaTemplate.send(responseTopic, response);
    }

    /**
     * ?????? ?????? ???????????? ???????????? ???????????????.
     */
    public void issueAtOrderCoupon(Long couponId, Long memberNo, LocalDateTime expiredAt) {
        OrderCoupon coupon = orderCouponRepository
            .findFirstByMemberIsNullAndCouponId(couponId)
            .orElseThrow(() -> new IllegalArgumentException(NO_STORAGE));

        coupon.setIssuedAt(LocalDateTime.now());
        coupon.setMemberNo(memberNo);
        coupon.setExpiredAt(expiredAt);

        orderCouponRepository.save(coupon);
    }

    /**
     * ?????? ?????? ???????????? ???????????? ???????????????.
     */
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
