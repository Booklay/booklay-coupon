package com.nhnacademy.booklay.booklaycoupon.controller.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/**
 *
 * @author 김승혜
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
@Slf4j
public class CouponAdminController {

    private final CouponAdminService couponAdminService;
    private final CouponIssueService couponIssueService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCURequest couponRequest) {
        couponAdminService.createCoupon(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/pages")
    public ResponseEntity<PageResponse<CouponRetrieveResponse>> retrieveAllCoupons(@PageableDefault Pageable pageable) {
        Page<CouponRetrieveResponse> couponPage = couponAdminService.retrieveAllCoupons(pageable);

        PageResponse<CouponRetrieveResponse> couponPageResponse = new PageResponse<>(couponPage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponPageResponse);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailRetrieveResponse> retrieveCouponDetail(@PathVariable Long couponId) {
        CouponDetailRetrieveResponse couponDetailRetrieveResponse =
            couponAdminService.retrieveCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(couponDetailRetrieveResponse);
    }

    @PutMapping("/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable Long couponId,
                                             @Valid @RequestBody CouponCURequest couponRequest) {
        couponAdminService.updateCoupon(couponId, couponRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponAdminService.deleteCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/members/issue")
    public ResponseEntity<Void> issueCouponToMember(@Valid @RequestBody
                                                    CouponIssueToMemberRequest couponRequest) {
        couponIssueService.issueCouponToMember(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/issue")
    public ResponseEntity<Void> issueCoupon(@Valid @RequestBody
                                            CouponIssueRequest couponRequest) {
        couponIssueService.issueCoupon(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 쿠폰 발급 내역
    @GetMapping("/issue-history")
    public ResponseEntity<List<CouponHistoryRetrieveResponse>> retrieveCouponIssueHistory() {
        List<CouponHistoryRetrieveResponse> responses =
            couponAdminService.retrieveIssuedCoupons();

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    // 쿠폰 사용 내역
    @GetMapping("/history")
    public ResponseEntity<Void> retrieveCouponHistory() {
        return null;
    }
}
