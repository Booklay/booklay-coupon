package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponSettingRepository extends JpaRepository<CouponSetting, Long> {
    List<CouponSetting> findAllBySettingType(Integer settingType);
    Page<CouponSetting> findAllBySettingType(Integer settingType, Pageable pageable);
}
