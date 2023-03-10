package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
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
@WebMvcTest(CouponZoneController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponZoneControllerTest {

    @MockBean
    CouponZoneService couponZoneService;

    @Autowired
    CouponZoneController couponZoneController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String DOC_PREFIX = "member/coupon-zone";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    void testRetrieveCouponZoneLimited() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneLimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/limited"))
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

        Mockito.verify(couponZoneService).retrieveCouponZoneLimited(any());
    }

    @Test
    void testRetrieveCouponZoneUnlimited() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneUnlimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/unlimited"))
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

        Mockito.verify(couponZoneService).retrieveCouponZoneUnlimited(any());
    }

    @Test
    void testRetrieveCouponZoneGraded() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponZoneResponse()), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneGraded(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/graded"))
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

        Mockito.verify(couponZoneService).retrieveCouponZoneGraded(any());
    }

    @Test
    @DisplayName("???????????? ????????? ????????? ?????? ?????? ??????")
    void testRetrieveCouponZoneInform() throws Exception {
        // given

        // when
        when(couponZoneService.retrieveCouponZoneInform(targetId)).thenReturn(Dummy.getDummyCouponZoneCheckResponse());

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/{couponId}", targetId))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("openedAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("issuanceDeadlineAt").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("grade").description("????????? ???????????? ??? ?????? ??????")),
                pathParameters(
                    parameterWithName("couponId").description("?????? ?????? ?????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneInform(1L);
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????? ????????? ??????")
    void issueNoLimitCoupon() throws Exception {
        // given
        CouponZoneIssueToMemberRequest request = Dummy.getDummyCouponZoneIssueToMemberRequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/no-limit")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotAcceptable())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("couponId").description("?????? ID"),
                    fieldWithPath("memberId").description("?????? ?????? ?????? ID")),
                responseFields(
                    fieldWithPath("message").description("?????? ?????? ????????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService).issueNoLimitCoupon(any(), any());
    }
}