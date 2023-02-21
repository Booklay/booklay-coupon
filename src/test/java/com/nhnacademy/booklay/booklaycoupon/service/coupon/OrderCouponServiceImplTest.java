package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class OrderCouponServiceImplTest {

    @InjectMocks
    OrderCouponServiceImpl orderCouponService;

    @Mock
    OrderCouponRepository orderCouponRepository;

    @Mock
    CouponJdbcRepository couponJdbcRepository;

    @Test
    void retrieveCouponByCouponCode() {
        // given
        String targetCode = "code";
        given(orderCouponRepository.findByCode(targetCode)).willReturn(notNull());

        // when
        orderCouponService.retrieveCouponByCouponCode(targetCode);

        // then
        BDDMockito.then(orderCouponRepository).should().findByCode(targetCode);
    }

    @Test
    void usingCoupon() {
        // given
        List<CouponUsingDto> categoryCouponList = List.of(new CouponUsingDto());
        Long targetId = 1L;

        // when
        orderCouponService.usingCoupon(categoryCouponList, targetId);

        // then
        BDDMockito.then(couponJdbcRepository).should().useOrderCoupons(anyList(), any(), any());
    }

    @Test
    void refundCoupon() {
        // given
        Long targetId = 1L;

        // when
        orderCouponService.refundCoupon(targetId, targetId);

        // then
        BDDMockito.then(couponJdbcRepository).should().refundOrderCoupons(any(), any());
    }
}