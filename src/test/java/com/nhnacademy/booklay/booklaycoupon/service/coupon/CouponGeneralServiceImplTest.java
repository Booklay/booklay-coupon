package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponGeneralServiceImplTest {

    @InjectMocks
    private CouponGeneralServiceImpl couponGeneralService;

    @Mock
    private ProductCouponService productCouponService;

    @Mock
    private OrderCouponService orderCouponService;

    @Test
    void testRetrieveCouponByCouponCode() {
        // given
        String couponCode = "code";
        when(productCouponService.retrieveCouponByCouponCode(couponCode)).thenReturn(null);

        // when
        couponGeneralService.retrieveCouponByCouponCode(couponCode);

        // then
        BDDMockito.then(orderCouponService).should().retrieveCouponByCouponCode(couponCode);
    }

    @Test
    void retrieveCouponByCouponCodeList() {
    }

    @Test
    void couponUsing() {
    }

    @Test
    void couponRefund() {
    }
}