package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CouponWelcomeService {

    private final GetCouponService couponService;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    @Value("${coupon.welcome.couponId}")
    private String welcomeCouponId;

    /**
     * 사용자에게 웰컴쿠폰을 지급합니다.
     * 웰컴 쿠폰의 id는 configuration 파일에서 설정합니다.
     */
    public void issueWelcomeCoupon(Long memberNo) {
        Long couponId = Long.parseLong(welcomeCouponId);
        Coupon coupon = couponService.checkCouponExist(couponId);
        String isOrderOrProduct = couponService.isOrderOrProduct(coupon);

        Long validateTerm = 7L;

        if (isOrderOrProduct.equals("product")) {
            ProductCoupon productCoupon =
                new ProductCoupon(coupon, CodeUtils.getProductCouponCode());
            productCoupon.setMemberNo(memberNo);
            productCoupon.setIssuedAt(LocalDateTime.now());
            productCoupon.setExpiredAt(productCoupon.getIssuedAt().plusDays(validateTerm));

            productCouponRepository.save(productCoupon);
        } else {
            OrderCoupon orderCoupon =
                new OrderCoupon(coupon, CodeUtils.getOrderCouponCode(), false);
            orderCoupon.setMemberNo(memberNo);
            orderCoupon.setIssuedAt(LocalDateTime.now());
            orderCoupon.setExpiredAt(orderCoupon.getIssuedAt().plusDays(validateTerm));

            orderCouponRepository.save(orderCoupon);
        }
    }
}
