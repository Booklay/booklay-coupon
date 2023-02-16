package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members/{memberNo}/coupons")
@RequiredArgsConstructor
public class CouponMemberController {

    private final CouponMemberService couponMemberService;

    /**
     * 회원이 소유한 쿠폰을 조회합니다.
     */
    @GetMapping
    public ResponseEntity<PageResponse<MemberCouponRetrieveResponse>> retrieveCouponsByMember(@PathVariable Long memberNo,
                                                                                              @PageableDefault Pageable pageable) {
        Page<MemberCouponRetrieveResponse> pages =
            couponMemberService.retrieveCoupons(memberNo, pageable);

        PageResponse<MemberCouponRetrieveResponse> response = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    /**
     * 회원이 소유한 쿠폰의 개수를 조회합니다.
     */
    @GetMapping("/count")
    public ResponseEntity<Void> retrieveCouponCountByMember(@PathVariable Long memberNo) {
        couponMemberService.retrieveCouponCount(memberNo);
        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

    @GetMapping("/point")
    public ResponseEntity<PageResponse<PointCouponRetrieveResponse>> retrievePointCoupons(@PathVariable Long memberNo,
                                                                                          @PageableDefault Pageable pageable) {
        Page<PointCouponRetrieveResponse> pages =
            couponMemberService.retrievePointCoupons(memberNo, pageable);

        PageResponse<PointCouponRetrieveResponse> response = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

    @PostMapping("/point/{couponId}")
    public ResponseEntity<Void> usePointCoupon(@PathVariable Long memberNo, @PathVariable Long couponId) {
        return null;
    }
}
