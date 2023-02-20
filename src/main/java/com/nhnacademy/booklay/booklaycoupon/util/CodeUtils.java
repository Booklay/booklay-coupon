package com.nhnacademy.booklay.booklaycoupon.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CodeUtils {

    public static String getProductCouponCode() {
        return "P" + UUID.randomUUID().toString().substring(0, 30);
    }

    public static String getOrderCouponCode() {
        return "O" + UUID.randomUUID().toString().substring(0, 30);
    }
}
