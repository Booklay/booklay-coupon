package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.Mockito.when;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
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
    void testCouponUsing() {
        // given
        CouponUseRequest request = Dummy.getDummyCouponUseRequest();

        // when
        couponGeneralService.couponUsing(request);

        // then
        BDDMockito.then(productCouponService).should().usingCoupon(request.getProductCouponList());
        BDDMockito.then(orderCouponService).should().usingCoupon(request.getCategoryCouponList());
    }

    @Test
    void testCouponRefund() {
        // given
        CouponRefundRequest request = Dummy.getDummyCouponRefundRequest();

        // when
        couponGeneralService.couponRefund(request);

        // then
        BDDMockito.then(productCouponService).should().refundCoupon(request.getOrderProductNoList());
        BDDMockito.then(orderCouponService).should().refundCoupon(request.getOrderNo());
    }
}