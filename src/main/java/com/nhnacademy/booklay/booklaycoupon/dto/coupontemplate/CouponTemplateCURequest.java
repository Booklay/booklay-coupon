package com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateCURequest {
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull
    private Long typeCode;
    @NotNull
    private Integer amount;
    @NotNull
    private Boolean isOrderCoupon;
    @NotNull
    private Long applyItemId;
    @NotNull
    private Integer minimumUseAmount;
    private Integer maximumDiscountAmount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime issuingDeadLine;
    private Integer validateTerm;
    @NotNull
    private Boolean isDuplicatable;

    private String imagePath;

    public CouponTemplate toEntity(Image image) {
        return CouponTemplate.builder()
            .name(name)
            .typeCode(typeCode)
            .amount(amount)
            .isOrderCoupon(isOrderCoupon)
            .applyItemId(applyItemId)
            .minimumUseAmount(minimumUseAmount)
            .maximumDiscountAmount(maximumDiscountAmount)
            .issuingDeadLine(issuingDeadLine)
            .validateTerm(validateTerm)
            .isDuplicatable(isDuplicatable)
            .image(image)
            .build();
    }
}
