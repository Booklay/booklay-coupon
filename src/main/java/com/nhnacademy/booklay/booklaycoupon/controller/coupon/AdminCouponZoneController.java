package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.service.couponzone.CouponZoneService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리자의 쿠폰존 관리 요청을 받는 컨트롤러.
 * @author 김승혜
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupon-zone")
public class AdminCouponZoneController {

    private final CouponZoneService couponZoneService;

    @GetMapping("/limited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneLimited(@PageableDefault
                                                                               Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveAdminLimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    @GetMapping("/unlimited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneUnlimited(@PageableDefault
                                                                                      Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveAdminUnlimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createAtCoupon(@Valid @RequestBody
                                             CouponZoneCreateRequest couponRequest) {
        couponZoneService.createAtCouponZone(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{couponZoneId}")
    public ResponseEntity<Void> deleteAtCouponZone(@PathVariable Long couponZoneId) {
        couponZoneService.deleteAtCouponZone(couponZoneId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
