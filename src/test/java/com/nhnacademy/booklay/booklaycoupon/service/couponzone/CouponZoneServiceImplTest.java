package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.GetCouponService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponZoneServiceImplTest {

    @InjectMocks
    private CouponZoneServiceImpl couponZoneService;

    @Mock
    private CouponZoneRepository couponZoneRepository;

    @Mock
    private GetCouponService couponService;

    @Test
    @DisplayName("관리자의 이달의 쿠폰 조회 성공")
    void retrieveAdminLimited() {
        // given

        // when
        couponZoneService.retrieveAdminLimited(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByIsLimitedIsAndGradeIs(any(), any(), any());
    }

    @Test
    @DisplayName("관리자의 등급별 쿠폰 조회 성공")
    void retrieveAdminCouponZoneGraded() {
        // given

        // when
        couponZoneService.retrieveAdminCouponZoneGraded(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByGradeIsNot(any(), any());
    }

    @Test
    @DisplayName("관리자의 무제한 쿠폰 조회 성공")
    void retrieveAdminUnlimited() {
        // given

        // when
        couponZoneService.retrieveAdminUnlimited(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByIsLimitedIsAndGradeIs(any(), any(), any());
    }

    @Test
    @DisplayName("사용자의 이달의 쿠폰 조회 성공")
    void retrieveCouponZoneLimited() {
        // given

        // when
        couponZoneService.retrieveCouponZoneUnlimited(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByIsLimitedIsAndIsBlindIsFalseAndGradeIs(any(), any(), any());
    }

    @Test
    @DisplayName("사용자의 무제한 쿠폰 조회 성공")
    void retrieveCouponZoneUnlimited() {
        // given

        // when
        couponZoneService.retrieveCouponZoneUnlimited(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByIsLimitedIsAndIsBlindIsFalseAndGradeIs(any(), any(), any());
    }

    @Test
    @DisplayName("사용자의 등급별 쿠폰 조회")
    void retrieveCouponZoneGraded() {
        // given

        // when
        couponZoneService.retrieveCouponZoneGraded(PageRequest.of(1, 1));

        // then
        BDDMockito.then(couponZoneRepository).should().findAllByGradeIsNotAndIsBlindIsFalse(any(), any());
    }

    @Test
    @DisplayName("관리자의 쿠폰존에 쿠폰 등록 성공")
    void createAtCouponZone() {
        // given
        CouponZoneCreateRequest request =
            Dummy.getDummyCouponZoneCreateRequest();
        given(couponService.checkCouponExist(any())).willReturn(Dummy.getDummyCoupon());

        // when
        couponZoneService.createAtCouponZone(request);

        // then
        BDDMockito.then(couponZoneRepository).should().save(any());
    }

    @Test
    void deleteAtCouponZone() {
    }

    @Test
    void retrieveCouponZoneIsBlind() {
    }

    @Test
    void updateIsBlind() {
    }

    @Test
    void retrieveCouponZoneInform() {
    }

    @Test
    void issueNoLimitCoupon() {
    }
}