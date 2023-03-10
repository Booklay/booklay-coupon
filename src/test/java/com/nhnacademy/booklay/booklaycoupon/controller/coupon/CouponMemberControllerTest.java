package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponMemberService;
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
@WebMvcTest(CouponMemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponMemberControllerTest {

    @MockBean
    CouponMemberService couponMemberService;

    @Autowired
    CouponMemberController couponMemberController;

    @Autowired
    MockMvc mockMvc;

    String DOC_PREFIX = "members/memberNo/coupons";
    String URI_PREFIX = "/members/{memberNo}/coupons";
    Long targetId = 1L;

    @Test
    @DisplayName("????????? ????????? ?????? ?????? ??????.")
    void testRetrieveCouponsByMember() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<MemberCouponRetrieveResponse> response = new PageImpl<>(List.of(
            Dummy.getDummyMemberCouponRetrieveResponse()), pageRequest, 1);

        // when
        when(couponMemberService.retrieveCoupons(targetId, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX, targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].amount").description("?????? ?????????"),
                    fieldWithPath("data.[].couponType").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].itemId").description("?????? ?????? ?????? ID"),
                    fieldWithPath("data.[].minimumUseAmount").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].maximumDiscountAmount").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].expiredAt").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].isDuplicatable").description("?????? ?????? ?????? ??????"),
                    fieldWithPath("data.[].isUsed").description("?????? ??????"),
                    fieldWithPath("data.[].reason").description("?????? ??????(?????? ??????, ?????? ??????, ?????? ??????)")),
                pathParameters(
                    parameterWithName("memberNo").description("?????? No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCoupons(any(), any());
    }

    @Test
    @DisplayName("????????? ????????? ????????? ?????? ?????? ??????.")
    void testRetrieveCouponCountByMember() throws Exception {
        // given

        // when
        when(couponMemberService.retrieveCouponCount(targetId)).thenReturn(1);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/count", targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("couponCount").description("????????? ????????? ?????? ??????")),
                pathParameters(
                    parameterWithName("memberNo").description("?????? No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCouponCount(1L);
    }



    @Test
    @DisplayName("????????? ?????? ????????? ?????? ??????")
    void testRetrievePointCoupons() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<PointCouponRetrieveResponse> couponRetrieveResponses = new PageImpl<>(List.of(Dummy.getDummyPointCouponRetrieveResponse()), pageRequest, 1);

        // when
        when(couponMemberService.retrievePointCoupons(1L, pageRequest)).thenReturn(couponRetrieveResponses);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/point", targetId)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page").description("??????????????? ?????????"),
                    parameterWithName("size").description("????????? ??????")),
                responseFields(
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].couponId").description("?????? ID"),
                    fieldWithPath("data.[].orderCouponId").description("????????? ?????? ID. ????????? ????????? ?????? ????????? ??????."),
                    fieldWithPath("data.[].name").description("?????? ??????"),
                    fieldWithPath("data.[].amount").description("???????????? ??????????????? ??????")),
                pathParameters(
                    parameterWithName("memberNo").description("?????? No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrievePointCoupons(any(), any());
    }

    @Test
    @DisplayName("????????? ????????? ?????? ??????")
    void testUsePointCoupon() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.post(URI_PREFIX + "/point/{orderCouponId}", targetId, targetId))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberNo").description("?????? No"),
                    parameterWithName("orderCouponId").description("???????????? ????????? ?????? ??????(????????? ??????) ID"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).usePointCoupon(any(), any());
    }

    @Test
    @DisplayName("????????? ????????? ?????????????????? ?????? ??????")
    void testCheckUsedPointCoupon() throws Exception {
        // given

        // when
        when(couponMemberService.checkUsedPointCoupon(targetId, targetId)).thenReturn(true);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/point/used/{orderCouponId}", targetId, targetId))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberNo").description("?????? No"),
                    parameterWithName("orderCouponId").description("???????????? ????????? ?????? ??????(????????? ??????) ID"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).checkUsedPointCoupon(targetId, targetId);
    }
}
