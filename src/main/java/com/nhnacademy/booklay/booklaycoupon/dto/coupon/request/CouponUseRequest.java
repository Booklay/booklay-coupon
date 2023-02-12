package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import java.util.List;
import lombok.Getter;

@Getter
public class CouponUseRequest {

    List<CouponUsingDto> productCouponList;
    List<CouponUsingDto> categoryCouponList;
}
