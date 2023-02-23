package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponWelcomeServiceTest {

    @InjectMocks
    private CouponWelcomeService couponWelcomeService;

    @Mock
    private GetCouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private OrderCouponRepository orderCouponRepository;

    @Mock
    private ProductCouponRepository productCouponRepository;

    @Value("${coupon.welcome.couponId}")
    private String welcomeCouponId = "1";

    Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = Dummy.getDummyCoupon();

        ReflectionTestUtils.setField(couponWelcomeService,
            "welcomeCouponId",
            "1");

    }

    @Test
    @DisplayName("회원 가입 쿠폰 발급 성공 :: 상품 쿠폰일 때.")
    void issueWelcomeCoupon_ProductCoupon() {
        // given
        Long targetId = 1L;
        given(couponService.checkCouponExist(targetId)).willReturn(coupon);
        given(couponService.isOrderOrProduct(any())).willReturn("product");

        // when
        couponWelcomeService.issueWelcomeCoupon(targetId);

        // then
        BDDMockito.then(productCouponRepository).should().save(any());
    }

    @Test
    @DisplayName("회원 가입 쿠폰 발급 성공 :: 주문 쿠폰일 때.")
    void issueWelcomeCoupon_OrderCoupon() {
        // given
        Long couponId = Long.parseLong(welcomeCouponId);
        Long targetId = 1L;

        given(couponService.checkCouponExist(couponId)).willReturn(coupon);
        given(couponService.isOrderOrProduct(any())).willReturn("order");

        // when
        couponWelcomeService.issueWelcomeCoupon(targetId);

        // then
        BDDMockito.then(orderCouponRepository).should().save(any());
    }

}