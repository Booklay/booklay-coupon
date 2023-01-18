package com.nhnacademy.booklay.booklaycoupon.service.couponTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTemplateRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponTemplateServiceImplTest {
    @InjectMocks
    private CouponTemplateServiceImpl couponTemplateService;

    @Mock
    private CouponTemplateRepository couponTemplateRepository;

    CouponTemplate couponTemplate;
    List<CouponTemplate> couponTemplateList;
    CouponTemplateCURequest couponTemplateCURequest;

    @BeforeEach
    void setUp() {
        couponTemplate = Dummy.getCouponTemplate();
        couponTemplateList = List.of(couponTemplate);
        couponTemplateCURequest = Dummy.getCouponTemplateCURequest();
    }
    @Test
    @DisplayName("쿠폰템플릿 생성 테스트")
    void testCreateCouponTemplate() {
        couponTemplateService.createCouponTemplate(couponTemplateCURequest);

        // then
        BDDMockito.then(couponTemplateRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰템플릿 단건 조회 테스트")
    void testRetrieveCouponTemplate() {
        // given
        Long targetId = 1L;
        given(couponTemplateRepository.findById(targetId)).willReturn(Optional.ofNullable(couponTemplate));

        // when
        couponTemplateService.retrieveCouponTemplate(targetId);

        // then
        BDDMockito.then(couponTemplateRepository).should().findById(targetId);
    }

    @Test
    @DisplayName("쿠폰템플릿 페이지 조회 테스트")
    void testRetrieveAllCouponTemplate() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(couponTemplateRepository.findAllBy(pageRequest)).willReturn(Page.empty());

        // when
        couponTemplateService.retrieveAllCouponTemplate(pageRequest);

        // then
        BDDMockito.then(couponTemplateRepository).should().findAllBy(pageRequest);
    }

    @Test
    @DisplayName("쿠폰템플릿 DetailResponse 조회 테스트")
    void testRetrieveCouponTemplateDetailResponse() {
        Long targetId = 1L;
        given(couponTemplateRepository.findById(targetId)).willReturn(Optional.ofNullable(couponTemplate));

        // when
        couponTemplateService.retrieveCouponTemplateDetailResponse(targetId);

        // then
        BDDMockito.then(couponTemplateRepository).should().findById(targetId);
    }

    @Test
    void testUpdateCouponTemplate() {
        // given
        Long targetId = couponTemplate.getId();

        given(couponTemplateRepository.findById(targetId)).willReturn(Optional.ofNullable(couponTemplate));
        // when
        couponTemplateService.updateCouponTemplate(couponTemplate.getId(), couponTemplateCURequest);

        // then
        BDDMockito.then(couponTemplateRepository).should().save(any());
    }

    @Test
    void testDeleteCouponTemplate() {
        // given
        Long targetId = 1L;

        // when
        couponTemplateService.deleteCouponTemplate(targetId);

        // then
        BDDMockito.then(couponTemplateRepository).should().deleteById(targetId);
    }
}