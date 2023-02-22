package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.service.couponsetting.CouponSettingService;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponSettingAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponSettingAdminControllerTest {

    @MockBean
    CouponSettingService couponSettingService;

    @Autowired
    CouponSettingAdminController couponSettingAdminController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    CouponSetting couponSetting;
    List<CouponSetting> couponSettingList;
    CouponSettingCURequest couponSettingCURequest;

    String DOC_PREFIX = "admin/couponSettings";
    String URI_PREFIX = "/" + DOC_PREFIX;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        couponSetting = Dummy.getCouponSetting();
        couponSettingList = List.of(couponSetting);
        couponSettingCURequest = Dummy.getCouponSettingCURequest();
    }

    @Test
    void createCouponSetting() throws Exception {
        //given

        //when

        //then
        mockMvc.perform(post(URI_PREFIX)
            .content(objectMapper.writeValueAsString(couponSettingCURequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("settingType").description("자동 발급 유형"),
                    fieldWithPath("couponTemplateNo").type(JsonFieldType.NUMBER).description("쿠폰 템플릿 No"),
                    fieldWithPath("memberGrade").description("회원 등급"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).createSetting(any());
    }

    @Test
    void retrieveAllCouponSettings() throws Exception {
        //given

        //mocking
        given(couponSettingService.retrieveAllSetting()).willReturn(couponSettingList);

        //then
        mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isOk())
            .andExpect(result -> result.getResponse().getContentAsString().equals(objectMapper.writeValueAsString(couponSettingList)))
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].id").description("쿠폰 ID"),
                    fieldWithPath("[].settingType").description("자동 발급 유형"),
                    fieldWithPath("[].couponTemplateNo").type(JsonFieldType.NUMBER).description("쿠폰 템플릿 No"),
                    fieldWithPath("[].memberGrade").description("회원 등급"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).retrieveAllSetting();
    }

    @Test
    void retrieveAllCouponSetting_byPage() throws Exception {
        //given
        Page<CouponSetting> page = new PageImpl<>(couponSettingList);

        //mocking
        given(couponSettingService.retrieveAllSettingPage(any())).willReturn(page);

        //then
        mockMvc.perform(get(URI_PREFIX+"/pages"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['data']").isArray())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].id").description("쿠폰 ID"),
                    fieldWithPath("data.[].settingType").description("자동 발급 유형"),
                    fieldWithPath("data.[].couponTemplateNo").description("쿠폰 템플릿 No"),
                    fieldWithPath("data.[].memberGrade").description("회원 등급"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).retrieveAllSettingPage(any());
    }
    @Test
    void retrieveAllCouponSettings_bySettingType() throws Exception {
        //given

        //mocking
        given(couponSettingService.retrieveSettings(any())).willReturn(couponSettingList);
        //then
        mockMvc.perform(
                RestDocumentationRequestBuilders.get(URI_PREFIX+"/type/{settingType}", couponSettingCURequest.getSettingType()))
            .andExpect(status().isOk())
            .andExpect(result -> result.getResponse().getContentAsString().equals(objectMapper.writeValueAsString(couponSettingList)))
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("[].id").description("쿠폰 ID"),
                    fieldWithPath("[].settingType").description("자동 발급 유형"),
                    fieldWithPath("[].couponTemplateNo").description("쿠폰 템플릿 No"),
                    fieldWithPath("[].memberGrade").description("회원 등급")),
                pathParameters(
                    parameterWithName("settingType").description("자동 발급 유형"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).retrieveSettings(any());
    }

    @Test
    void retrieveAllCouponSettings_byPageAndSettingType() throws Exception {
        //given
        Page<CouponSetting> page = new PageImpl<>(couponSettingList);

        //mocking
        given(couponSettingService.retrieveSettingsPage(any(), any())).willReturn(page);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX+"/pages/{settingType}", couponSetting.getSettingType()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['data']").isArray())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].id").description("쿠폰 ID"),
                    fieldWithPath("data.[].settingType").description("자동 발급 유형"),
                    fieldWithPath("data.[].couponTemplateNo").description("쿠폰 템플릿 No"),
                    fieldWithPath("data.[].memberGrade").description("회원 등급")),
                pathParameters(
                    parameterWithName("settingType").description("자동 발급 유형"))
            ))
            .andReturn();
        then(couponSettingService).should(times(1)).retrieveSettingsPage(any(), any());
    }

    @Test
    void retrieveCouponSettingDetail() throws Exception {
        //given
        given(couponSettingService.retrieveSetting(any())).willReturn(couponSetting);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/{couponSettingId}", couponSetting.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$['id']").value(couponSetting.getId()))
            .andExpect(jsonPath("$['settingType']").value(couponSetting.getSettingType()))
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("id").description("쿠폰 ID"),
                    fieldWithPath("settingType").description("자동 발급 유형"),
                    fieldWithPath("couponTemplateNo").description("쿠폰 템플릿 No"),
                    fieldWithPath("memberGrade").description("회원 등급")),
                pathParameters(
                    parameterWithName("couponSettingId").description("자동 발급 쿠폰 대상 ID"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).retrieveSetting(any());
    }

    @Test
    void updateCouponSetting() throws Exception {
        //given
        given(couponSettingService.updateSetting(any(), any())).willReturn(couponSetting);

        //then
        mockMvc.perform(RestDocumentationRequestBuilders.put(URI_PREFIX+"/{couponSettingId}", couponSetting.getId())
            .content(objectMapper.writeValueAsString(couponSettingCURequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("settingType").description("자동 발급 유형"),
                    fieldWithPath("couponTemplateNo").description("쿠폰 템플릿 No"),
                    fieldWithPath("memberGrade").description("회원 등급")),
                pathParameters(
                    parameterWithName("couponSettingId").description("자동 발급 쿠폰 대상 ID"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).updateSetting(any(), any());
    }

    @Test
    void deleteCouponSetting() throws Exception {
        //given

        //when
        mockMvc.perform(RestDocumentationRequestBuilders.delete(URI_PREFIX+"/{couponSettingId}", couponSetting.getId()))
                .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponSettingId").description("자동 발급 쿠폰 대상 ID"))
            ))
            .andReturn();

        then(couponSettingService).should(times(1)).deleteSetting(any());
    }
}