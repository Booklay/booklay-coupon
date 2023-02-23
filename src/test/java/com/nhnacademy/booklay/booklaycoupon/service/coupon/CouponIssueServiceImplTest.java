package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponJdbcRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponIssueServiceImplTest {

    @InjectMocks
    private CouponIssueServiceImpl couponIssueService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CouponJdbcRepository couponJdbcRepository;

    @Mock
    private OrderCouponRepository orderCouponRepository;

    @Mock
    private ProductCouponRepository productCouponRepository;

    @Mock
    private GetCouponService couponService;

    Coupon orderCoupon;
    Coupon productCoupon;

    @BeforeEach
    void setUp() {
        orderCoupon = Dummy.getDummyCoupon();
        productCoupon = Dummy.getDummyCoupon();

        ReflectionTestUtils.setField(orderCoupon, "category", Dummy.getDummyCategory());
        ReflectionTestUtils.setField(productCoupon, "product", Dummy.getDummyProduct());
    }

    @Test
    @DisplayName("사용자에게 주문쿠폰 발급 테스트")
    void testIssueOrderCouponToMember() {
        // given
        CouponIssueToMemberRequest request = new CouponIssueToMemberRequest(1L, 1L, LocalDateTime.now());

        given(couponService.checkCouponExist(1L)).willReturn(orderCoupon);
        given(memberRepository.findById(request.getMemberId())).willReturn(
            Optional.ofNullable(Dummy.getDummyMember()));


        // when
        couponIssueService.issueCouponToMember(request);

        // then
        BDDMockito.then(orderCouponRepository).should().save(any());
    }

    @Test
    @DisplayName("사용자에게 상품쿠폰 발급 테스트")
    void testIssueProductCouponToMember() {
        // given
        CouponIssueToMemberRequest request = new CouponIssueToMemberRequest(1L, 1L, LocalDateTime.now());

        given(couponService.checkCouponExist(1L)).willReturn(productCoupon);
        given(memberRepository.findById(request.getMemberId())).willReturn(
            Optional.ofNullable(Dummy.getDummyMember()));

        // when
        couponIssueService.issueCouponToMember(request);

        // then
        BDDMockito.then(productCouponRepository).should().save(any());
    }

    @Test
    @DisplayName("사용자에게 유효하지 않은 쿠폰 발급시 실패")
    void testInvalidCouponToMember() {
        // given
        CouponIssueToMemberRequest request = new CouponIssueToMemberRequest(1L, 1L, LocalDateTime.now());

        given(couponService.checkCouponExist(1L)).willReturn(Dummy.getDummyCoupon());
        given(memberRepository.findById(request.getMemberId())).willReturn(
            Optional.ofNullable(Dummy.getDummyMember()));


        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            couponIssueService.issueCouponToMember(request);
        });
    }

    @Test
    @DisplayName("수량만큼의 주문쿠폰 발급 테스트")
    void testIssueOrderCoupon() {
        // given
        CouponIssueRequest request = new CouponIssueRequest(1L, 50);
        given(couponService.checkCouponExist(1L)).willReturn(orderCoupon);

        // when
        couponIssueService.issueCoupon(request);

        // then
        BDDMockito.then(couponJdbcRepository).should().saveOrderCoupons(1L, 50);
    }

    @Test
    @DisplayName("수량만큼의 상품쿠폰 발급 테스트")
    void testIssueProductCoupon() {
        // given
        CouponIssueRequest request = new CouponIssueRequest(1L, 50);
        given(couponService.checkCouponExist(request.getCouponId())).willReturn(productCoupon);

        // when
        couponIssueService.issueCoupon(request);

        // then
        BDDMockito.then(couponJdbcRepository).should().saveProductCoupons(1L, 50);
    }

    @Test
    @DisplayName("유효하지 않은 쿠폰 발급시 실패")
    void testIssueInvalidCoupon() {
        // given
        CouponIssueRequest request = new CouponIssueRequest(1L, 50);
        given(couponService.checkCouponExist(request.getCouponId())).willReturn(Dummy.getDummyCoupon());

        // then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            couponIssueService.issueCoupon(request);
        });
    }
}
