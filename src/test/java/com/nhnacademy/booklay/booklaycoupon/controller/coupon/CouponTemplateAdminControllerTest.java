package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.controller.coupon.CouponTemplateAdminController;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponTemplate.CouponTemplateRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.service.couponTemplate.CouponTemplateService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CouponTemplateAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponTemplateAdminControllerTest {
    @MockBean
    CouponTemplateService couponTemplateService;

    @Autowired
    CouponTemplateAdminController couponTemplateAdminController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    CouponTemplate couponTemplate;
    CouponTemplateRetrieveResponse couponTemplateRetrieveResponse;
    List<CouponTemplateRetrieveResponse> couponTemplateRetrieveResponseList;
    CouponTemplateCURequest couponTemplateCURequest;
    CouponTemplateDetailRetrieveResponse couponTemplateDetailRetrieveResponse;


    private static final String URI_PREFIX = "/admin/couponTemplates";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        couponTemplate = Dummy.getCouponTemplate();
        couponTemplateRetrieveResponse = new CouponTemplateRetrieveResponse(
            couponTemplate.getId(),
            couponTemplate.getName(),
            couponTemplate.getIsOrderCoupon(),
            couponTemplate.getApplyItemId(),
            couponTemplate.getAmount(),
            couponTemplate.getMinimumUseAmount(),
            couponTemplate.getMaximumDiscountAmount(),
            couponTemplate.getIsDuplicatable());
        couponTemplateRetrieveResponseList = List.of(couponTemplateRetrieveResponse);
        couponTemplateCURequest = Dummy.getCouponTemplateCURequest();
        couponTemplateDetailRetrieveResponse = new CouponTemplateDetailRetrieveResponse(
            1L, "default", 1L,"더미주문쿠폰", true, 1L
            , 100, 100, 100
            , LocalDateTime.now(), 7, false
        );

    }
    @Test
    void createCouponTemplate() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post(URI_PREFIX)
            .content(objectMapper.writeValueAsString(couponTemplateCURequest))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isCreated());
        then(couponTemplateService).should(times(1)).createCouponTemplate(any());
    }

    @Test
    void retrieveAllCouponTemplates() throws Exception {
        //given
        Page<CouponTemplateRetrieveResponse> page = new PageImpl<>(couponTemplateRetrieveResponseList);

        //mocking
        given(couponTemplateService.retrieveAllCouponTemplate(any())).willReturn(page);

        //then
        mockMvc.perform(get(URI_PREFIX+"/pages"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['data']").isArray())
            .andReturn();
        then(couponTemplateService).should(times(1)).retrieveAllCouponTemplate(any());
    }

    @Test
    void retrieveCouponTemplateDetail() throws Exception {
        //given
        given(couponTemplateService.retrieveCouponTemplateDetailResponse(any())).willReturn(couponTemplateDetailRetrieveResponse);

        //when
        ResultActions result = mockMvc.perform(get(URI_PREFIX + "/" + couponTemplate.getId())
            .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$['id']").value(couponTemplateDetailRetrieveResponse.getId()))
            .andExpect(jsonPath("$['name']").value("더미주문쿠폰"));
        then(couponTemplateService).should(times(1)).retrieveCouponTemplateDetailResponse(any());
    }

    @Test
    void updateCouponTemplate() throws Exception {
        //given
        given(couponTemplateService.updateCouponTemplate(any(), any())).willReturn(couponTemplate);
        //when
        ResultActions result = mockMvc.perform(put(URI_PREFIX+"/"+couponTemplate.getId())
            .content(objectMapper.writeValueAsString(couponTemplateCURequest))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk());
        then(couponTemplateService).should(times(1)).updateCouponTemplate(any(), any());
    }

    @Test
    void deleteCouponTemplate() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete(URI_PREFIX+"/"+couponTemplate.getId()));

        //then
        result.andExpect(status().isOk());
        then(couponTemplateService).should(times(1)).deleteCouponTemplate(any());
    }
}