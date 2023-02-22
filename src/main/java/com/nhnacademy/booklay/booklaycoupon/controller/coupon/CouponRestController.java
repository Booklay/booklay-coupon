package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponGeneralService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("coupons")
public class CouponRestController {

    private final CouponGeneralService couponGeneralService;
    @GetMapping("code")
    public ResponseEntity<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCode(
            @RequestParam(value = "couponCode") String couponCode){
        CouponRetrieveResponseFromProduct couponRetrieveResponse = couponGeneralService.retrieveCouponByCouponCode(couponCode);
        return ResponseEntity.ok(couponRetrieveResponse);
    }

    @GetMapping("codes")
    public ResponseEntity<List<CouponRetrieveResponseFromProduct>> retrieveCouponByCouponCodeList(
            @RequestParam(value = "couponCodeList") List<String> couponCodeList,@RequestParam(value = "member_info_memberNo") Long memberNo){
        List<CouponRetrieveResponseFromProduct> couponRetrieveResponseFromProductList =
            couponGeneralService.retrieveCouponByCouponCodeList(couponCodeList, memberNo);
        return ResponseEntity.ok(couponRetrieveResponseFromProductList);
    }


    @PostMapping("using")
    public ResponseEntity<Void> useCoupons(@RequestBody CouponUseRequest couponUseRequest){
        couponGeneralService.couponUsing(couponUseRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("refund")
    public ResponseEntity<Void> refundCoupons(@RequestBody CouponRefundRequest couponRefundRequest){
        couponGeneralService.couponRefund(couponRefundRequest);
        return ResponseEntity.ok().build();
    }
}
