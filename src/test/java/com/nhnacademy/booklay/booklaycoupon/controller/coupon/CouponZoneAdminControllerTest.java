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
    @DisplayName("?????? ????????? ?????? ?????? ??????")
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
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].id").description("????????? ID"),
                    fieldWithPath("data.[].couponId").description("?????? ID"),
                    fieldWithPath("data.[].fileId").description("?????? ????????? ?????? ID"),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].description").description("???????????? ????????? ??????"),
                    fieldWithPath("data.[].grade").description("????????? ???????????? ??? ?????? ??????"),
                    fieldWithPath("data.[].openedAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].expiredAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].isBlind").description("??????????????? ????????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminLimited(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????? ??????")
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
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].id").description("????????? ID"),
                    fieldWithPath("data.[].couponId").description("?????? ID"),
                    fieldWithPath("data.[].fileId").description("?????? ????????? ?????? ID"),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].description").description("???????????? ????????? ??????"),
                    fieldWithPath("data.[].grade").description("????????? ???????????? ??? ?????? ??????"),
                    fieldWithPath("data.[].openedAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].expiredAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].isBlind").description("??????????????? ????????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminUnlimited(any());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ??????")
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
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].id").description("????????? ID"),
                    fieldWithPath("data.[].couponId").description("?????? ID"),
                    fieldWithPath("data.[].fileId").description("?????? ????????? ?????? ID"),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].description").description("???????????? ????????? ??????"),
                    fieldWithPath("data.[].grade").description("????????? ???????????? ??? ?????? ??????"),
                    fieldWithPath("data.[].openedAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].issuanceDeadlineAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].expiredAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].isBlind").description("??????????????? ????????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveAdminCouponZoneGraded(any());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ??????")
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
                    fieldWithPath("couponId").description("?????? ID"),
                    fieldWithPath("description").description("???????????? ????????? ??????"),
                    fieldWithPath("grade").description("????????? ???????????? ??? ?????? ??????"),
                    fieldWithPath("openedAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("issuanceDeadlineAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("expiredAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("isBlind").description("??????????????? ????????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).createAtCouponZone(any());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??????")
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
                    parameterWithName("couponZoneId").description("????????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).retrieveCouponZoneIsBlind(targetId);
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ??????")
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
                    fieldWithPath("isBlind").description("??????????????? ????????? ?????? ??????")),
                pathParameters(
                    parameterWithName("couponZoneId").description("????????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).updateIsBlind(eq(targetId), any());
    }

    @Test
    @DisplayName("??????????????? ?????? ?????? ??????")
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
                    parameterWithName("couponZoneId").description("????????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService, times(1)).deleteAtCouponZone(targetId);
    }
}