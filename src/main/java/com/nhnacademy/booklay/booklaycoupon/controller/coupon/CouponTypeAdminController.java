package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.response.CouponTypeRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupontype.CouponTypeService;
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

/**
 *
 * @author 김승혜
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/couponTypes")
public class CouponTypeAdminController {

    private final CouponTypeService couponTypeService;

    @GetMapping
    public ResponseEntity<PageResponse<CouponTypeRetrieveResponse>> retrieveAllCouponTypes(@PageableDefault
                                                                                           Pageable pageable) {
        Page<CouponTypeRetrieveResponse> couponTypePage = couponTypeService.retrieveAllCouponTypes(pageable);
        PageResponse<CouponTypeRetrieveResponse> couponTypePageResponse = new PageResponse<>(couponTypePage);

        return ResponseEntity.status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(couponTypePageResponse);
    }

    @PostMapping
    public ResponseEntity<Void> createCouponType(@Valid @RequestBody
                                                 CouponTypeCURequest couponTypeRequest) {
       couponTypeService.createCouponType(couponTypeRequest);

       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{couponTypeId}")
    public ResponseEntity<Void> updateCouponType(@PathVariable Long couponTypeId,
                                                 @Valid @RequestBody CouponTypeCURequest couponTypeRequest) {
        couponTypeService.updateCouponType(couponTypeId, couponTypeRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{couponTypeId}")
    public ResponseEntity<Void> deleteCouponType(@PathVariable Long couponTypeId) {
        couponTypeService.deleteCouponType(couponTypeId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
