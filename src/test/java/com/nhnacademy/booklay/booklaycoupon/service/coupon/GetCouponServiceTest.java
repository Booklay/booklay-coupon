package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class GetCouponServiceTest {

    @InjectMocks
    private GetCouponService getCouponService;

    @Mock
    private CouponRepository couponRepository;

    @Test
    @DisplayName("쿠폰이 존재하는지 확인 성공")
    void checkCouponExist() {
        // given
        Coupon coupon = Dummy.getDummyCoupon();
        Long targetId = 1L;

        given(couponRepository.findById(targetId)).willReturn(
            Optional.ofNullable(coupon));

        // when
        getCouponService.checkCouponExist(targetId);

        // then
        BDDMockito.then(couponRepository).should().findById(targetId);
    }

    @Test
    @DisplayName("쿠폰의 적용 대상 확인 성공 :: 주문")
    void isOrderOrProduct_Order() {
        // given
        Coupon orderCoupon = Dummy.getDummyCoupon_Order();

        // when

        // then
        assertThat(getCouponService.isOrderOrProduct(orderCoupon)).isEqualTo("order");
    }

    @Test
    @DisplayName("쿠폰의 적용 대상 확인 성공 :: 상품")
    void isOrderOrProduct_Product() {
        // given
        Coupon productCoupon = Dummy.getDummyCoupon_Product();

        // when

        // then
        assertThat(getCouponService.isOrderOrProduct(productCoupon)).isEqualTo("product");
    }
}