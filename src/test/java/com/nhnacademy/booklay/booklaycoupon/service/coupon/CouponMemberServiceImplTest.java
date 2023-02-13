package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponMemberServiceImplTest {

    @InjectMocks
    private CouponMemberServiceImpl couponMemberService;

    @Mock
    CouponRepository couponRepository;

//    @Test
//    void retrieveCoupons() {
//        // given
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        given(couponRepository.getCouponsByMember(1L)).willReturn(Page.empty());
//
//        // when
//        couponMemberService.retrieveCoupons(1L, pageRequest);
//
//        // then
//        BDDMockito.then(couponRepository).should().getCouponsByMember(1L);
//    }
//
//    @Test
//    void retrievePointCoupons() {
//        // given
//        PageRequest pageRequest = PageRequest.of(0, 10);
//        given(couponRepository.getPointCouponByMember(1L, pageRequest)).willReturn(Page.empty());
//
//        // when
//        couponMemberService.retrievePointCoupons(1L, pageRequest);
//
//        // then
//        BDDMockito.then(couponRepository).should().getPointCouponByMember(any(), any());
//    }
}
