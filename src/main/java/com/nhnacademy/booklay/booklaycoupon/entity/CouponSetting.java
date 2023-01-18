package com.nhnacademy.booklay.booklaycoupon.entity;

import com.nhnacademy.booklay.booklaycoupon.dto.couponSetting.CouponSettingCURequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon_setting")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponSetting {
    @Id
    @Column(name = "setting_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "setting_type")
    private Integer settingType;
    @Column(name = "coupon_template_no")
    private Long couponTemplateNo;
    @Column(name = "member_grade")
    private Long memberGrade;

    @Builder
    public CouponSetting(Integer settingType, Long couponTemplateNo,
                         Long memberGrade) {
        this.settingType = settingType;
        this.couponTemplateNo = couponTemplateNo;
        this.memberGrade = memberGrade;
    }

    public void update(CouponSettingCURequest couponSettingCURequest) {
        this.settingType = couponSettingCURequest.getSettingType();
        this.couponTemplateNo = couponSettingCURequest.getCouponTemplateNo();
        this.memberGrade = couponSettingCURequest.getMemberGrade();
    }
}
