package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.couponzone.CouponZoneService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponZoneAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponZoneAdminControllerTest {

    @MockBean
    CouponZoneService couponZoneService;

    @Autowired
    CouponZoneAdminController couponZoneAdminController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String DOC_PREFIX = "admin/coupon-zone";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    @DisplayName("제한 수량의 쿠폰 조회 성공")
    void testRetrieveCouponZoneLimited() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveAdminLimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/limited")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].id").description("쿠폰존 ID"),
                    fieldWithPath("data.[].couponId").description("쿠폰 ID"),
                    fieldWithPath("data.[].fileId").description("쿠폰 이미지 파일 ID"),
                    fieldWithPath("data.[].name").description("쿠폰 이름"),
                    fieldWithPath("data.[].description").description("쿠폰존에 등록될 설명"),
                    fieldWithPath("data.[].grade").description("쿠폰을 발급받을 수 있는 등급"),
                    fieldWithPath("data.[].openedAt").description("쿠폰 발급 가능 시간"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("쿠폰 발급 종료 시간"),
                    fieldWithPath("data.[].expiredAt").description("쿠폰 사용 만료 날짜"),
                    fieldWithPath("data.[].isBlind").description("쿠폰존에서 쿠폰의 숨김 여부"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminLimited(any());
    }

    @Test
    @DisplayName("수량 제한 없는 쿠폰 조회 성공")
    void testRetrieveCouponZoneUnlimited() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveAdminUnlimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/unlimited")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].id").description("쿠폰존 ID"),
                    fieldWithPath("data.[].couponId").description("쿠폰 ID"),
                    fieldWithPath("data.[].fileId").description("쿠폰 이미지 파일 ID"),
                    fieldWithPath("data.[].name").description("쿠폰 이름"),
                    fieldWithPath("data.[].description").description("쿠폰존에 등록될 설명"),
                    fieldWithPath("data.[].grade").description("쿠폰을 발급받을 수 있는 등급"),
                    fieldWithPath("data.[].openedAt").description("쿠폰 발급 가능 시간"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("쿠폰 발급 종료 시간"),
                    fieldWithPath("data.[].expiredAt").description("쿠폰 사용 만료 날짜"),
                    fieldWithPath("data.[].isBlind").description("쿠폰존에서 쿠폰의 숨김 여부"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminUnlimited(any());
    }

    @Test
    @DisplayName("등급별 쿠폰 조회 성공")
    void testRetrieveCouponZoneGraded() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveAdminCouponZoneGraded(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/graded")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].id").description("쿠폰존 ID"),
                    fieldWithPath("data.[].couponId").description("쿠폰 ID"),
                    fieldWithPath("data.[].fileId").description("쿠폰 이미지 파일 ID"),
                    fieldWithPath("data.[].name").description("쿠폰 이름"),
                    fieldWithPath("data.[].description").description("쿠폰존에 등록될 설명"),
                    fieldWithPath("data.[].grade").description("쿠폰을 발급받을 수 있는 등급"),
                    fieldWithPath("data.[].openedAt").description("쿠폰 발급 가능 시간"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("쿠폰 발급 종료 시간"),
                    fieldWithPath("data.[].expiredAt").description("쿠폰 사용 만료 날짜"),
                    fieldWithPath("data.[].isBlind").description("쿠폰존에서 쿠폰의 숨김 여부"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminCouponZoneGraded(any());
    }

    @Test
    @DisplayName("쿠폰존에 쿠폰 등록 성공")
    void testCreateAtCoupon() throws Exception {

        // given
        CouponZoneCreateRequest request = Dummy.getDummyCouponZoneCreateRequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("couponId").description("쿠폰 ID"),
                    fieldWithPath("description").description("쿠폰존에 등록될 설명"),
                    fieldWithPath("grade").description("쿠폰을 발급받을 수 있는 등급"),
                    fieldWithPath("openedAt").description("쿠폰 발급 가능 시간"),
                    fieldWithPath("issuanceDeadlineAt").description("쿠폰 발급 종료 시간"),
                    fieldWithPath("expiredAt").description("쿠폰 사용 만료 날짜"),
                    fieldWithPath("isBlind").description("쿠폰존에서 쿠폰의 숨김 여부"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).createAtCouponZone(any());
    }

    @Test
    @DisplayName("쿠폰의 숨김 여부 조회 성공")
    void testGetIsBlind() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/blind/{couponZoneId}", targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponZoneId").description("쿠폰존 ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveCouponZoneIsBlind(targetId);
    }

    @Test
    @DisplayName("쿠폰의 숨김 여부 수정 성공")
    void testUpdateIsBlind() throws Exception {

        // given
        CouponZoneIsBlindRequest request= Dummy.getDummyCouponZoneIsBlindRequest();

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.post(URI_PREFIX + "/blind/{couponZoneId}", targetId)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("isBlind").description("쿠폰존에서 쿠폰의 숨김 여부")),
                pathParameters(
                    parameterWithName("couponZoneId").description("쿠폰존 ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).updateIsBlind(eq(targetId), any());
    }

    @Test
    @DisplayName("쿠폰존에서 쿠폰 삭제 성공")
    void testDeleteAtCouponZone() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.delete(URI_PREFIX + "/{couponZoneId}", targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponZoneId").description("쿠폰존 ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).deleteAtCouponZone(targetId);
    }
}