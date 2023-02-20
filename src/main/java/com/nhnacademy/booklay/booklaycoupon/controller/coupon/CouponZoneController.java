package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneCheckResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.service.couponzone.CouponZoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member/coupon-zone")
public class CouponZoneController {

    private final CouponZoneService couponZoneService;

    /**
     * isLimited = true 인 쿠폰존의 쿠폰을 조회합니다. :: 이달의 쿠폰
     */
    @GetMapping("/limited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneLimited(@PageableDefault Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveCouponZoneLimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * isLimited = false 인 쿠폰존의 쿠폰을 조회합니다. :: 무제한 쿠폰
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
     * graded != '제한 없음' 인 쿠폰존의 쿠폰을 조회합니다. :: 등급별 쿠폰
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

    /**
     * 사용자가 쿠폰존에서 쿠폰을 발급 받기 위해, 쿠폰의 정보를 조회합니다.
     * 오픈시간, 발급 마감 시간, 등급을 조회합니다.
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponZoneCheckResponse> retrieveCouponZoneInform(@PathVariable Long couponId) {
        CouponZoneCheckResponse couponZoneTimeResponse =
            couponZoneService.retrieveCouponZoneInform(couponId);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponZoneTimeResponse);
    }

    @PostMapping("/no-limit")
    public ResponseEntity<Map> issueNoLimitCoupon(@RequestBody CouponZoneIssueToMemberRequest request) {
        String message =
            couponZoneService.issueNoLimitCoupon(request.getCouponId(), request.getMemberId());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
            .body(Collections.singletonMap("message", message));
    }
}
