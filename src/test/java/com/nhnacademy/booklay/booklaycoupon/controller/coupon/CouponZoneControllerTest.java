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

        Mockito.verify(couponZoneService).retrieveCouponZoneGraded(any());
    }

    @Test
    @DisplayName("쿠폰존에 등록된 쿠폰의 정보 조회 성공")
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
                    fieldWithPath("openedAt").description("쿠폰 발급 가능 시간"),
                    fieldWithPath("issuanceDeadlineAt").description("쿠폰 발급 종료 시간"),
                    fieldWithPath("grade").description("쿠폰을 발급받을 수 있는 등급")),
                pathParameters(
                    parameterWithName("couponId").description("조회 대상 쿠폰 ID"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneInform(1L);
    }

    @Test
    @DisplayName("수량 제한이 없는 쿠폰 발급 테스트 성공")
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
                    fieldWithPath("couponId").description("쿠폰 ID"),
                    fieldWithPath("memberId").description("발급 대상 회원 ID")),
                responseFields(
                    fieldWithPath("message").description("쿠폰 발급 요청에 대한 응답"))
            ))
            .andReturn();

        Mockito.verify(couponZoneService).issueNoLimitCoupon(any(), any());
    }
}