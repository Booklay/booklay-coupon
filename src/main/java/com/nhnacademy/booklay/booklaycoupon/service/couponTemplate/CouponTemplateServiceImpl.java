package com.nhnacademy.booklay.booklaycoupon.service.couponTemplate;

import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTemplateRepository;
import com.nhnacademy.booklay.booklaycoupon.service.couponType.CouponTypeService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
/**
 *
 * @author 오준후
 */
@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService{
    private final CouponTemplateRepository couponTemplateRepository;
    private final CouponTypeService couponTypeService;
    @Override
    public CouponTemplate createCouponTemplate(
        @Valid CouponTemplateCURequest couponTemplateCreateRequest) {
        //todo imageService 완성시 변경
        CouponTemplate couponTemplate = couponTemplateCreateRequest.toEntity(1L);
        return couponTemplateRepository.save(couponTemplate);
    }

    @Override
    public CouponTemplate retrieveCouponTemplate(Long couponTemplateNum) {
        return couponTemplateRepository.findById(couponTemplateNum)
            .orElseThrow(() -> new NotFoundException("CouponTemplate", couponTemplateNum));
    }

    @Override
    public Page<CouponTemplateRetrieveResponse> retrieveAllCouponTemplate(Pageable pageable) {
        return couponTemplateRepository.findAllBy(pageable);
    }

    @Override
    public CouponTemplateDetailRetrieveResponse retrieveCouponTemplateDetailResponse(
        Long couponTemplateId) {
        return CouponTemplateDetailRetrieveResponse.fromEntity(couponTemplateRepository.findById(couponTemplateId)
            .orElseThrow(() -> new IllegalArgumentException("No Such CouponTemplate.")));
    }

    @Override
    public CouponTemplate updateCouponTemplate(Long couponTemplateId,
        CouponTemplateCURequest couponTemplateCURequest){
        CouponTemplate couponTemplate = couponTemplateRepository.findById(couponTemplateId)
            .orElseThrow(() -> new NotFoundException("CouponTemplate", couponTemplateId));
//        couponTypeService.retrieveCouponType(couponTemplateCURequest.getTypeCode());
        //todo imageService 완성시 변경
        couponTemplate.update(couponTemplateCURequest, 0L);
        return couponTemplateRepository.save(couponTemplate);
    }

    @Override
    public void deleteCouponTemplate(Long couponTemplateId) {
        couponTemplateRepository.deleteById(couponTemplateId);
    }
}
