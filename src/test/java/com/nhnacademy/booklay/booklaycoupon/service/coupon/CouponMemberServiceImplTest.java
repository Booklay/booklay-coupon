package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberOrderCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponMemberServiceImplTest {

    @InjectMocks
    private CouponMemberServiceImpl couponMemberService;

    @Mock
    CouponRepository couponRepository;

    @Mock
    OrderCouponRepository orderCouponRepository;

    @Mock
    ProductCouponRepository productCouponRepository;

    @Test
    @DisplayName("사용자가 소유한 쿠폰 조회 성공")
    void testRetrieveCoupons() {
        // given
        Long targetId = 1L;
        List<MemberOrderCouponRetrieveResponse> orderList = List.of();
        List<MemberCouponRetrieveResponse> productCouponList = List.of();
        PageRequest pageRequest = PageRequest.of(0, 10);

        given(orderCouponRepository.getCouponsByMember(targetId)).willReturn(orderList);
        given(productCouponRepository.getCouponsByMember(targetId)).willReturn(productCouponList);

        // when
        couponMemberService.retrieveCoupons(targetId, pageRequest);

        // then
        BDDMockito.then(orderCouponRepository).should().getCouponsByMember(targetId);
        BDDMockito.then(productCouponRepository).should().getCouponsByMember(targetId);
    }


    @Test
    @DisplayName("사용자의 포인트 쿠폰 조회 성공")
    void testRetrievePointCoupons() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        couponMemberService.retrievePointCoupons(1L, pageRequest);

        // then
        BDDMockito.then(couponRepository).should().getPointCouponByMember(eq(1L), any());
    }

    @Test
    @DisplayName("사용자가 소유한 쿠폰의 갯수 조회 성공")
    void testRetrieveCouponCount() {
        // given
        Long targetId = 1L;
        List<MemberOrderCouponRetrieveResponse> orderList = List.of();
        List<MemberCouponRetrieveResponse> productCouponList = List.of(Dummy.getDummyMemberCouponRetrieveResponse());

        given(orderCouponRepository.getCouponsByMember(targetId)).willReturn(orderList);
        given(productCouponRepository.getCouponsByMember(targetId)).willReturn(productCouponList);

        // when
        couponMemberService.retrieveCouponCount(targetId);

        // then
        BDDMockito.then(orderCouponRepository).should().getCouponsByMember(targetId);
        BDDMockito.then(productCouponRepository).should().getCouponsByMember(targetId);
    }
}
