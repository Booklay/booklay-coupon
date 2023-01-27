package com.nhnacademy.booklay.booklaycoupon.dto.couponsetting;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponSettingCURequest {


    @NotNull
    private Integer settingType;
    @NotNull
    private Long couponTemplateNo;
    @NotNull
    private Long memberGrade;

    public CouponSetting toEntity() {
        return CouponSetting.builder()
            .settingType(settingType)
            .couponTemplateNo(couponTemplateNo)
            .memberGrade(memberGrade)
            .build();
    }
}
