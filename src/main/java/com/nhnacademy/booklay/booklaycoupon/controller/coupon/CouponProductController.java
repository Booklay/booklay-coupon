package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.PageResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.ProductCouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/coupons/product")
@RequiredArgsConstructor
public class CouponProductController {

    private final ProductCouponService productCouponService;

    @GetMapping("/{productNo}")
    public ResponseEntity<PageResponse<CouponRetrieveResponseFromProduct>> retrieveAllCoupons(@PageableDefault Pageable pageable,
                                                                                              @PathVariable Long productNo,
                                                                                              MemberInfo memberInfo,
                                                                                              @RequestParam Boolean isDuplicable) {
        Page<CouponRetrieveResponseFromProduct> couponPage = productCouponService.retrieveCouponPageByMemberNoAndProductNo(memberInfo.getMemberNo(), productNo, isDuplicable,  pageable);
        PageResponse<CouponRetrieveResponseFromProduct> couponPageResponse = new PageResponse<>(couponPage);

        return ResponseEntity.ok(couponPageResponse);
    }
}
