package com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request;

import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponTypeCURequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    public static CouponType toEntity(CouponTypeCURequest couponTypeRequest) {
        return CouponType.builder()
            .id(couponTypeRequest.getId())
            .name(couponTypeRequest.getName())
            .build();
    }
}
