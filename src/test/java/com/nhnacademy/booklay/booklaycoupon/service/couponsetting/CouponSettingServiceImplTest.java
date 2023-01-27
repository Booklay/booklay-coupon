package com.nhnacademy.booklay.booklaycoupon.service.couponsetting;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponSettingRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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
class CouponSettingServiceImplTest {

    @InjectMocks
    private CouponSettingServiceImpl couponSettingService;

    @Mock
    private CouponSettingRepository couponSettingRepository;


    CouponSetting couponSetting;
    List<CouponSetting> couponList;
    CouponType couponType;
    CouponSettingCURequest couponSettingCURequest;
    Category category;

    @BeforeEach
    void setUp() {
        couponSetting = Dummy.getCouponSetting();
        couponList = List.of(couponSetting);
        couponSettingCURequest = Dummy.getCouponSettingCURequest();
    }

    @Test
    @DisplayName("설정 생성 테스트")
    void testCreateSetting() {

        // given

        // when
        couponSettingService.createSetting(couponSettingCURequest);

        // then
        BDDMockito.then(couponSettingRepository).should().save(any());
    }

    @Test
    @DisplayName("모든 설정 조회 테스트")
    void testRetrieveAllSetting() {
        // given
        // when
        couponSettingService.retrieveAllSetting();

        // then
        BDDMockito.then(couponSettingRepository).should().findAll();
    }

    @Test
    @DisplayName("모든 설정 조회 테스트 type")
    void testRetrieveSettings() {
        // given
        Integer settingType = 1;
        // when
        couponSettingService.retrieveSettings(settingType);

        // then
        BDDMockito.then(couponSettingRepository).should().findAllBySettingType(settingType);
    }

    @Test
    @DisplayName("모든 설정 조회 테스트 page")
    void testRetrieveAllSettingPage() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        couponSettingService.retrieveAllSettingPage(pageRequest);

        // then
        BDDMockito.then(couponSettingRepository).should().findAll(pageRequest);
    }

    @Test
    @DisplayName("모든 설정 조회 테스트 type + page")
    void testRetrieveSettingsPage() {
        // given
        Integer settingType = 1;
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        couponSettingService.retrieveSettingsPage(settingType, pageRequest);

        // then
        BDDMockito.then(couponSettingRepository).should().findAllBySettingType(settingType, pageRequest);
    }

    @Test
    @DisplayName("설정 단건 조회 테스트")
    void testRetrieveSetting() {

        // given
        Long targetId = 1L;
        given(couponSettingRepository.findById(targetId)).willReturn(Optional.ofNullable(couponSetting));

        // when
        couponSettingService.retrieveSetting(targetId);

        // then
        BDDMockito.then(couponSettingRepository).should().findById(targetId);
    }

    @Test
    @DisplayName("설정 수정 테스트")
    void testUpdateCoupon() {

        // given
        Long targetId = 1L;
        given(couponSettingRepository.findById(targetId)).willReturn(Optional.ofNullable(couponSetting));
        // when
        couponSettingService.updateSetting(couponSetting.getId(), couponSettingCURequest);

        // then
        BDDMockito.then(couponSettingRepository).should().save(any());
    }

    @Test
    @DisplayName("설정 삭제 테스트")
    void testDeleteCoupon() {

        // given
        Long targetId = 1L;

        // when
        couponSettingService.deleteSetting(targetId);

        // then
        BDDMockito.then(couponSettingRepository).should().deleteById(targetId);
    }
}