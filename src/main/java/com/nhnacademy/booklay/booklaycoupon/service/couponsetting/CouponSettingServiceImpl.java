package com.nhnacademy.booklay.booklaycoupon.service.couponsetting;

import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponSettingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponSettingServiceImpl implements CouponSettingService {
    private final CouponSettingRepository couponSettingRepository;
    @Override
    public CouponSetting createSetting(CouponSettingCURequest couponSettingCURequest){
        return couponSettingRepository.save(couponSettingCURequest.toEntity());
    }

    @Override
    public CouponSetting retrieveSetting(Long settingId) {
        return couponSettingRepository.findById(settingId).orElseThrow(() -> new NotFoundException("CouponSetting", settingId));
    }

    @Override
    public List<CouponSetting> retrieveAllSetting() {
        return couponSettingRepository.findAll();
    }
    @Override
    public List<CouponSetting> retrieveSettings(Integer settingType){
        return couponSettingRepository.findAllBySettingType(settingType);
    }
    @Override
    public Page<CouponSetting> retrieveAllSettingPage(Pageable pageable){
        return couponSettingRepository.findAll(pageable);
    }
    @Override
    public Page<CouponSetting> retrieveSettingsPage(Integer settingType, Pageable pageable){
        return couponSettingRepository.findAllBySettingType(settingType, pageable);
    }
    @Override
    public CouponSetting updateSetting(Long settingNo,
                                       CouponSettingCURequest couponSettingCURequest){
        CouponSetting couponSetting = couponSettingRepository.findById(settingNo)
            .orElseThrow(() -> new NotFoundException("CouponSetting", settingNo));
        couponSetting.update(couponSettingCURequest);
        return couponSettingRepository.save(couponSetting);
    }
    @Override
    public void deleteSetting(Long settingNo){
        couponSettingRepository.deleteById(settingNo);
    }
}
