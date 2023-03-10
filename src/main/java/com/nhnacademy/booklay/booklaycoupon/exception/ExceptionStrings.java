package com.nhnacademy.booklay.booklaycoupon.exception;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionStrings {
    public static final String NOT_REGISTERED_COUPON = "등록되지 않은 쿠폰입니다.";
    public static final String ALREADY_ISSUED = "이미 발급 받은 쿠폰입니다.";
    public static final String NO_STORAGE = "수량이 모두 소진되었습니다.";
}
