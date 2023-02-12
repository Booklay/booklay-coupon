package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import java.util.List;
import lombok.Getter;

@Getter
public class CouponRefundRequest {
    private List<Long> orderProductNoList;
    private Long orderNo;
}
