package com.nhnacademy.booklay.booklaycoupon.controller.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupontemplate.CouponTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author 오준후
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/couponTemplates")
public class CouponTemplateAdminController {
    private final CouponTemplateService couponTemplateService;

    @PostMapping
    public ResponseEntity<Void> createCouponTemplate(@Valid @RequestBody
                                                     CouponTemplateCURequest couponTemplateCURequest) {
        couponTemplateService.createCouponTemplate(couponTemplateCURequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/pages")
    public ResponseEntity<PageResponse<CouponTemplateRetrieveResponse>> retrieveAllCouponTemplates(@PageableDefault
                                                                                               Pageable pageable) {
        Page<CouponTemplateRetrieveResponse> couponTemplatePage = couponTemplateService.retrieveAllCouponTemplate(pageable);
        PageResponse<CouponTemplateRetrieveResponse> couponTemplatePageResponse = new PageResponse<>(couponTemplatePage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponTemplatePageResponse);
    }

    @GetMapping("/{couponTemplateId}")
    public ResponseEntity<CouponTemplateDetailRetrieveResponse> retrieveCouponTemplate(
        @PathVariable Long couponTemplateId) {
        CouponTemplateDetailRetrieveResponse couponTemplateDetailRetrieveResponse =
            couponTemplateService.retrieveCouponTemplateDetailResponse(couponTemplateId);

        return ResponseEntity.status(HttpStatus.OK)
            .body(couponTemplateDetailRetrieveResponse);
    }

    @PutMapping("/{couponTemplateId}")
    public ResponseEntity<Void> updateCouponTemplate(@Valid @RequestBody
                                                 CouponTemplateCURequest couponTemplateCURequest,
                                             @PathVariable Long couponTemplateId) {
        couponTemplateService.updateCouponTemplate(couponTemplateId, couponTemplateCURequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{couponTemplateId}")
    public ResponseEntity<Void> deleteCouponTemplate(@PathVariable Long couponTemplateId) {
        couponTemplateService.deleteCouponTemplate(couponTemplateId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
