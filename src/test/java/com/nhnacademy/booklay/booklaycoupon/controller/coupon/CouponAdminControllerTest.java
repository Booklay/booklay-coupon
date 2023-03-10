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
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
import java.time.LocalDateTime;
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
@WebMvcTest(CouponAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponAdminControllerTest {

    @MockBean
    CouponAdminService couponAdminService;

    @MockBean
    CouponIssueService couponIssueService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String DOC_PREFIX = "admin/coupons";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????????")
    void testRetrieveAllCoupons() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponRetrieveResponse()), pageRequest, 1);

        // when
        when(couponAdminService.retrieveAllCoupons(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX)
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
                    fieldWithPath("data.[].id").description("?????? ID"),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].typeName").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].amount").description("?????????"),
                    fieldWithPath("data.[].minimumUseAmount").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].maximumDiscountAmount").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].isLimited").description("?????? ?????? ??????")),
                requestParameters(
                    parameterWithName("page").description("??????????????? ?????????"),
                    parameterWithName("size").description("????????? ??????"))
                ))
            .andReturn();

        Mockito.verify(couponAdminService).retrieveAllCoupons(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????????")
    void testCreateCoupon() throws Exception {

        // given
        CouponCURequest couponRequest = Dummy.getDummyOrderCouponCURequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX)
            .content(objectMapper.writeValueAsString(couponRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("fileId").description("?????? ????????? ID"),
                    fieldWithPath("typeCode").description("?????? ?????? ??????"),
                    fieldWithPath("amount").description("?????????"),
                    fieldWithPath("isOrderCoupon").description("?????? ?????? ??????"),
                    fieldWithPath("applyItemId").description("?????? ?????? ?????? ??????????????? ID"),
                    fieldWithPath("minimumUseAmount").description("?????? ?????? ??????"),
                    fieldWithPath("maximumDiscountAmount").description("?????? ?????? ??????"),
                    fieldWithPath("isDuplicatable").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("isLimited").description("?????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponAdminService).createCoupon(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????????")
    void testRetrieveCouponDetail() throws Exception {

        // given
        CouponDetailRetrieveResponse response = Dummy.getDummyCouponDetailRetrieveResponse();

        // when
        when(couponAdminService.retrieveCoupon(targetId)).thenReturn(response);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/{couponId}", targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("?????? ID"),
                    fieldWithPath("name").description("?????? ??????"),
                    fieldWithPath("typeName").description("?????? ?????? ??????"),
                    fieldWithPath("amount").description("?????????"),
                    fieldWithPath("applyItemId").description("?????? ?????? ?????? ??????????????? ID"),
                    fieldWithPath("itemName").description("?????? ?????? ???????????? ??????"),
                    fieldWithPath("minimumUseAmount").description("?????? ?????? ??????"),
                    fieldWithPath("maximumDiscountAmount").description("?????? ?????? ??????"),
                    fieldWithPath("isDuplicatable").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("isLimited").description("?????? ?????? ??????"),
                    fieldWithPath("objectFileId").description("?????? ????????? ????????? ID"),
                    fieldWithPath("isOrderCoupon").description("?????? ?????? ??????")),
                pathParameters(
                    parameterWithName("couponId").description("?????? ??????????????? ????????? ID")
                )
            ))
            .andReturn();

        Mockito.verify(couponAdminService).retrieveCoupon(targetId);
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ?????? ??? ?????? ?????????")
    void testRetrieveCouponDetailFail() throws Exception {

        // given

        // when
        when(couponAdminService.retrieveCoupon(targetId)).thenThrow(NotFoundException.class);

        // then
        mockMvc.perform(get(URI_PREFIX + "/" + targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).retrieveCoupon(targetId);
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????????")
    void testUpdateCoupon() throws Exception {

        // given
        CouponCURequest couponRequest = Dummy.getDummyOrderCouponCURequest();

        // when


        // then
        mockMvc.perform(RestDocumentationRequestBuilders.put(URI_PREFIX + "/{couponId}", targetId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponRequest)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponId").description("?????? ?????? ????????? ID")
                    ),
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("fileId").description("?????? ????????? ID"),
                    fieldWithPath("typeCode").description("?????? ?????? ??????"),
                    fieldWithPath("amount").description("?????????"),
                    fieldWithPath("isOrderCoupon").description("?????? ?????? ??????"),
                    fieldWithPath("applyItemId").description("?????? ?????? ?????? ??????????????? ID"),
                    fieldWithPath("minimumUseAmount").description("?????? ?????? ??????"),
                    fieldWithPath("maximumDiscountAmount").description("?????? ?????? ??????"),
                    fieldWithPath("isDuplicatable").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("isLimited").description("?????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).updateCoupon(eq(targetId), any());
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????????")
    void testUpdateCouponImage() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.put(URI_PREFIX + "/image/{couponId}/{objectFileId}", targetId ,targetId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponId").description("????????? ?????? ?????? ????????? ID"),
                    parameterWithName("objectFileId").description("??????????????? ???????????? ID")
                )
                ))
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).updateCouponImage(targetId, targetId);
    }

    @Test
    @DisplayName("?????? ????????? ?????? ?????? ?????????")
    void testDeleteCouponImage() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.put(URI_PREFIX + "/image/{couponId}", targetId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponId").description("???????????? ?????? ?????? ????????? ID")
                )
            ))
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).deleteCouponImage(targetId);
    }

    @Test
    @DisplayName("?????? ?????? ?????????")
    void testDeleteCoupon() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.delete(URI_PREFIX + "/{couponId}", targetId))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponId").description("?????? ?????? ????????? ID")
                )
            ))
            .andReturn();

        Mockito.verify(couponAdminService).deleteCoupon(1L);
    }

    @Test
    @DisplayName("???????????? ??????????????? ?????? ??????")
    void testIssueCouponToMember() throws Exception {

        // given
        CouponIssueToMemberRequest couponRequest = new CouponIssueToMemberRequest(1L, 1L, LocalDateTime.now());

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/members/issue")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("couponId").description("?????? ?????? ?????? ID"),
                    fieldWithPath("memberId").description("?????? ?????? ?????? No"),
                    fieldWithPath("expiredAt").description("?????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponIssueService).issueCouponToMember(any());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ??????")
    void testIssueCoupon() throws Exception {

        // given
        CouponIssueRequest couponRequest = new CouponIssueRequest(1L, 1);

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/issue")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("couponId").type(JsonFieldType.NUMBER).description("?????? ?????? ?????? ID"),
                    fieldWithPath("quantity").description("?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponIssueService).issueCoupon(any());
    }

    @Test
    @DisplayName("???????????? ?????? ?????? ??????")
    void testRetrieveCouponIssueHistory() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponHistoryRetrieveResponse> couponRetrieveResponses = new PageImpl<>(List.of(Dummy.getDummyCouponHistoryRetrieveResponse()), pageRequest, 1);

        // when
        when(couponAdminService.retrieveIssuedCoupons(any())).thenReturn(couponRetrieveResponses);

        // then
        mockMvc.perform(get(URI_PREFIX + "/issue-history")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].id").description("?????? ID"),
                    fieldWithPath("data.[].code").description("?????? ??????"),
                    fieldWithPath("data.[].couponName").description("?????? ??????"),
                    fieldWithPath("data.[].memberMemberId").description("?????? ?????? ?????? ID"),
                    fieldWithPath("data.[].issuedAt").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].expiredAt").description("?????? ?????? ??????"))
            ))
            .andReturn();

        Mockito.verify(couponAdminService).retrieveIssuedCoupons(any());
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????????")
    void testRetrieveCouponUsageHistory() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponUsedHistoryResponse> response = new PageImpl<>(List.of(Dummy.getDummyCouponUsedHistoryResponse()), pageRequest, 1);

        // when
        when(couponAdminService.retrieveUsedCoupon(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/history"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].memberId").description("?????? ?????? ?????? ID"),
                    fieldWithPath("data.[].couponName").description("?????? ??????"),
                    fieldWithPath("data.[].discountPrice").description("?????????"),
                    fieldWithPath("data.[].orderedAt").description("????????? ????????? ????????? ?????? ??????"),
                    fieldWithPath("data.[].issuedAt").description("?????? ?????? ??????"))
            ))
            .andReturn();
    }
}