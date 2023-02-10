package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneIsBlindResponse;
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
 * 관리자의 쿠폰존 요청을 받는 컨트롤러.
 * @author 김승혜
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupon-zone")
public class CouponZoneAdminController {

    private final CouponZoneService couponZoneService;

    /**
     * 제한 수량의 쿠폰을 조회합니다.(이달의 쿠폰)
     */
    @GetMapping("/limited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneLimited(@PageableDefault
                                                                               Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveAdminLimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * 수량 제한이 없는 쿠폰을 조회합니다.
     */
    @GetMapping("/unlimited")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneUnlimited(@PageableDefault
                                                                                      Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveAdminUnlimited(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * 등급별 쿠폰을 조회합니다.
     */
    @GetMapping("/graded")
    public ResponseEntity<PageResponse<CouponZoneResponse>> retrieveCouponZoneGraded(@PageableDefault
                                                                                        Pageable pageable) {
        Page<CouponZoneResponse> pages = couponZoneService.retrieveAdminCouponZoneGraded(pageable);
        PageResponse<CouponZoneResponse> pageResponse = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(pageResponse);
    }

    /**
     * 쿠폰존에 쿠폰을 등록합니다.
     * @param couponRequest 쿠폰존에 쿠폰을 등록학기 위한 객체입니다.
     */
    @PostMapping
    public ResponseEntity<Void> createAtCoupon(@Valid @RequestBody
                                             CouponZoneCreateRequest couponRequest) {
        couponZoneService.createAtCouponZone(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 쿠폰존에 등록된 쿠폰의 isBlind를 조회합니다.
     */
    @GetMapping("/blind/{couponZoneId}")
    public ResponseEntity<CouponZoneIsBlindResponse> getIsBlind(@PathVariable Long couponZoneId) {
        CouponZoneIsBlindResponse response = couponZoneService.retrieveCouponZoneIsBlind(couponZoneId);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
    }

    /**
     * 쿠폰존에 등록된 쿠폰의 숨김 여부를 수정합니다.
     */
    @PostMapping("/blind/{couponZoneId}")
    public ResponseEntity<Void> updateIsBlind(@PathVariable Long couponZoneId,
                                              @Valid @RequestBody CouponZoneIsBlindRequest request) {
        couponZoneService.updateIsBlind(couponZoneId, request);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 쿠폰존에서 쿠폰을 삭제합니다.
     */
    @DeleteMapping("/{couponZoneId}")
    public ResponseEntity<Void> deleteAtCouponZone(@PathVariable Long couponZoneId) {
        couponZoneService.deleteAtCouponZone(couponZoneId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
