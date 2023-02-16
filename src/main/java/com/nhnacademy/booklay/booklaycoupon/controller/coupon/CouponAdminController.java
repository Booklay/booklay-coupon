package com.nhnacademy.booklay.booklaycoupon.controller.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
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
 * 관리자의 쿠폰 관리 요청을 받는 컨트롤러.
 * @author 김승혜
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
@Slf4j
public class CouponAdminController {

    private final CouponAdminService couponAdminService;
    private final CouponIssueService couponIssueService;

    /**
     * 쿠폰 조회
     *
     */
    @GetMapping()
    public ResponseEntity<PageResponse<CouponRetrieveResponse>> retrieveAllCoupons(@PageableDefault Pageable pageable) {
        Page<CouponRetrieveResponse> couponPage = couponAdminService.retrieveAllCoupons(pageable);

        PageResponse<CouponRetrieveResponse> couponPageResponse = new PageResponse<>(couponPage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponPageResponse);
    }

    /**
     * 쿠폰 생성
     * @param couponRequest 쿠폰 생성 요청 객체
     */
    @PostMapping
    public ResponseEntity<Void> createCoupon(@Valid @RequestBody CouponCURequest couponRequest) {
        couponAdminService.createCoupon(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 쿠폰 상세 조회
     * @param couponId 조회할 쿠폰의 id
     */
    @GetMapping("/{couponId}")
    public ResponseEntity<CouponDetailRetrieveResponse> retrieveCouponDetail(@PathVariable Long couponId) {
        CouponDetailRetrieveResponse couponDetailRetrieveResponse =
            couponAdminService.retrieveCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(couponDetailRetrieveResponse);
    }

    /**
     * 쿠폰 정보 수정.
     * @param couponId 수정하려는 쿠폰의 id
     * @param couponRequest 쿠폰 수정에 필요한 요청 객체.
     */
    @PutMapping("/{couponId}")
    public ResponseEntity<Void> updateCoupon(@PathVariable Long couponId,
                                             @Valid @RequestBody CouponCURequest couponRequest) {
        couponAdminService.updateCoupon(couponId, couponRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 쿠폰의 이미지 수정
     * @param couponId 수정하려는 쿠폰의 id
     * @param objectFileId 오브젝트 스토리지 상의 Id
     */
    @PutMapping("/image/{couponId}/{objectFileId}")
    public ResponseEntity<Void> updateCouponImage(@PathVariable Long couponId, @PathVariable Long objectFileId) {
        couponAdminService.updateCouponImage(couponId, objectFileId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 쿠폰의 이미지 삭제
     */
    @PutMapping("/image/{couponId}")
    public ResponseEntity<Void> deleteCouponImage(@PathVariable Long couponId) {
        couponAdminService.deleteCouponImage(couponId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 쿠폰 삭제
     * @param couponId 삭제하려는 쿠폰의 id
     */
    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
        couponAdminService.deleteCoupon(couponId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 특정 사용자에게 쿠폰 발급
     * @param couponRequest 발급하려는 쿠폰의 id, 발급 대상 사용자의 id
     */
    @PostMapping("/members/issue")
    public ResponseEntity<Void> issueCouponToMember(@Valid @RequestBody
                                                    CouponIssueToMemberRequest couponRequest) {
        couponIssueService.issueCouponToMember(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 수량 만큼의 쿠폰 발급
     * @param couponRequest 발급하려는 쿠폰의
     */
    @PostMapping("/issue")
    public ResponseEntity<Void> issueCoupon(@Valid @RequestBody
                                            CouponIssueRequest couponRequest) {
        couponIssueService.issueCoupon(couponRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 발급된 쿠폰을 조회.
     * 소유되지 않은 쿠폰도 조회합니다.
     */
    @GetMapping("/issue-history")
    public ResponseEntity<PageResponse<CouponHistoryRetrieveResponse>> retrieveCouponIssueHistory(Pageable pageable) {
        Page<CouponHistoryRetrieveResponse> historyPage =
            couponAdminService.retrieveIssuedCoupons(pageable);

        PageResponse<CouponHistoryRetrieveResponse> response = new PageResponse<>(historyPage);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 사용된 쿠폰을 조회합니다.
     */
    @GetMapping("/history")
    public ResponseEntity<PageResponse<CouponUsedHistoryResponse>> retrieveCouponUsageHistory(@PageableDefault Pageable pageable) {
        Page<CouponUsedHistoryResponse> pages = couponAdminService.retrieveUsedCoupon(pageable);
        PageResponse<CouponUsedHistoryResponse> response = new PageResponse<>(pages);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
