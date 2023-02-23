package com.nhnacademy.booklay.booklaycoupon.dto.coupon.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDateTime expiredAt;
}