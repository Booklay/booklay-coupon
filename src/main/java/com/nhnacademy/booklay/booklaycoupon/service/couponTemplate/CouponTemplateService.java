package com.nhnacademy.booklay.booklaycoupon.service.couponTemplate;

import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCU;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponTemplateService {
    CouponTemplate createCouponTemplate(
        @Valid CouponTemplateCURequest couponTemplateCreateRequest);

    CouponTemplate retrieveCouponTemplate(Long couponTemplateNum);

    Page<CouponTemplateRetrieveResponse> retrieveAllCouponTemplate(Pageable pageable);

    CouponTemplateDetailRetrieveResponse retrieveCouponTemplateDetailResponse(Long couponTemplateId);

    CouponTemplate updateCouponTemplate(Long couponTemplateId,
        CouponTemplateCURequest couponTemplateCURequest);

    void deleteCouponTemplate(Long couponTemplateId);
}
