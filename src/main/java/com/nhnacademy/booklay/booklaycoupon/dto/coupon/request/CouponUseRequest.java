package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CouponUseRequest {

    List<CouponUsingDto> productCouponList;
    List<CouponUsingDto> categoryCouponList;
}
