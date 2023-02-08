package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.service.couponzone.CouponZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/coupon-zone")
public class CouponZoneController {

    private final CouponZoneService couponZoneService;

    /**
     * isLimited = true 인 쿠폰존의 쿠폰을 조회합니다.
     */
    @GetMapping("/limited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneLimited(
        @PageableDefault
        Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveCouponZoneLimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * isLimited = false 인 쿠폰존의 쿠폰을 조회합니다.
     */
    @GetMapping("/unlimited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneUnlimited(
        @PageableDefault
        Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveCouponZoneUnlimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * graded != null 인 쿠폰존의 쿠폰을 조회합니다.
     */
    @GetMapping("/graded")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneGraded(@PageableDefault
                                                                                     Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveCouponZoneGraded(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }
}
