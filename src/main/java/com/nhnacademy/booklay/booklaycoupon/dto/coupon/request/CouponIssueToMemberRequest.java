package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용자에게 쿠폰을 발급하기 위한 객체입니다.
 * 이 때에는, 만료일자를 지정해야합니다.
 */
@Getter
@RequiredArgsConstructor
public class CouponIssueToMemberRequest {

    @NotNull
    private final Long couponId;

    @NotNull
    private final Long memberId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private final LocalDateTime expiredAt;
}