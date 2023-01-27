package com.nhnacademy.booklay.booklaycoupon.service.couponsetting;

import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponSettingService {
    CouponSetting createSetting(CouponSettingCURequest couponSettingCURequest);

    CouponSetting retrieveSetting(Long settingId);
    List<CouponSetting> retrieveAllSetting();

    List<CouponSetting> retrieveSettings(Integer settingType);

    Page<CouponSetting> retrieveAllSettingPage(Pageable pageable);

    Page<CouponSetting> retrieveSettingsPage(Integer settingType, Pageable pageable);

    CouponSetting updateSetting(Long settingNo, CouponSettingCURequest couponSettingCURequest);

    void deleteSetting(Long settingNo);
}
