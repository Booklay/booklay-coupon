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
@RequestMapping("/admin/coupon-zone")
public class CouponZoneController {

    private final CouponZoneService couponZoneService;

    @GetMapping
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZone(@PageableDefault
                                                                               Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveCouponZone(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }
}
