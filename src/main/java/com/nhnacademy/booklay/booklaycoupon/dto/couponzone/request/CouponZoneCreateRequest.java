package com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponZoneCreateRequest {

    @NotNull
    private Long couponId;

    @NotNull
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime openedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime closedAt;

    @NotNull
    private Boolean isBlind;

    public static CouponZone toEntity(CouponZoneCreateRequest request, String name, Boolean isLimited, int maximum, Long couponId) {
        return CouponZone.builder()
            .name(name)
            .description(request.description)
            .openedAt(request.getOpenedAt())
            .closedAt(request.getClosedAt())
            .isBlind(request.isBlind)
            .couponId(couponId)
            .maximumDiscountAmount(maximum)
            .isLimited(isLimited)
            .build();
    }
}
