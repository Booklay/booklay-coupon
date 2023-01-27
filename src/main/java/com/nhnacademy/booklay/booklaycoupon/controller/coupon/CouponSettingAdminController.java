package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.service.couponsetting.CouponSettingService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/couponSettings")
public class CouponSettingAdminController {
    private final CouponSettingService couponSettingService;

    @PostMapping
    public ResponseEntity<Void> createCouponSetting(@Valid @RequestBody
                                                     CouponSettingCURequest couponSettingCURequest) {
        couponSettingService.createSetting(couponSettingCURequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/pages")
    public ResponseEntity<PageResponse<CouponSetting>> retrieveAllCouponSettingByPage(@PageableDefault
                                                                                               Pageable pageable) {
        Page<CouponSetting> couponSettingPage = couponSettingService.retrieveAllSettingPage(pageable);
        PageResponse<CouponSetting> couponSettingPageResponse = new PageResponse<>(couponSettingPage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponSettingPageResponse);
    }

    @GetMapping("/pages/{settingType}")
    public ResponseEntity<PageResponse<CouponSetting>> retrieveAllCouponSettingByPageAndSettingType(
        @PageableDefault Pageable pageable, @PathVariable Integer settingType) {
        Page<CouponSetting> couponSettingPage = couponSettingService.retrieveSettingsPage(settingType, pageable);
        PageResponse<CouponSetting> couponSettingPageResponse = new PageResponse<>(couponSettingPage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponSettingPageResponse);
    }

    @GetMapping()
    public ResponseEntity<List<CouponSetting>> retrieveAllCouponSetting(){
        List<CouponSetting> couponSettingList = couponSettingService.retrieveAllSetting();
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponSettingList);
    }

    @GetMapping("type/{settingType}")
    public ResponseEntity<List<CouponSetting>> retrieveAllCouponSettingBySettingType(
        @PathVariable Integer settingType){
        List<CouponSetting> couponSettingList = couponSettingService.retrieveSettings(settingType);
        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponSettingList);
    }

    @GetMapping("/{couponSettingId}")
    public ResponseEntity<CouponSetting> retrieveCouponSetting(
        @PathVariable Long couponSettingId) {
        CouponSetting couponSetting = couponSettingService.retrieveSetting(couponSettingId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(couponSetting);
    }

    @PutMapping("/{couponSettingId}")
    public ResponseEntity<Void> updateCouponSetting(@Valid @RequestBody
                                             CouponSettingCURequest couponSettingCURequest,
                                             @PathVariable Long couponSettingId) {
        couponSettingService.updateSetting(couponSettingId, couponSettingCURequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{couponSettingId}")
    public ResponseEntity<Void> deleteCouponSetting(@PathVariable Long couponSettingId) {
        couponSettingService.deleteSetting(couponSettingId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
