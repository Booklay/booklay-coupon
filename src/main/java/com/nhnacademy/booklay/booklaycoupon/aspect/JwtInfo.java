package com.nhnacademy.booklay.booklaycoupon.aspect;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class JwtInfo {

    private final String accessToken;

    private final String refreshToken;

    private final String uuid;
}
