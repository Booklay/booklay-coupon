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
    @DisplayName("회원이 소유한 쿠폰 조회 성공.")
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
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].name").description("쿠폰 이름"),
                    fieldWithPath("data.[].amount").description("쿠폰 할인량"),
                    fieldWithPath("data.[].couponType").description("쿠폰 타입 이름"),
                    fieldWithPath("data.[].itemId").description("쿠폰 적용 상품 ID"),
                    fieldWithPath("data.[].minimumUseAmount").description("최소 사용 금액"),
                    fieldWithPath("data.[].maximumDiscountAmount").description("최대 할인 금액"),
                    fieldWithPath("data.[].expiredAt").description("쿠폰 만료 날짜"),
                    fieldWithPath("data.[].isDuplicatable").description("중복 사용 가능 여부"),
                    fieldWithPath("data.[].isUsed").description("사용 여부"),
                    fieldWithPath("data.[].reason").description("쿠폰 상태(사용 가능, 사용 완료, 기간 만료)")),
                pathParameters(
                    parameterWithName("memberNo").description("회원 No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCoupons(any(), any());
    }

    @Test
    @DisplayName("회원이 소유한 쿠폰의 개수 조회 성공.")
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
                    fieldWithPath("couponCount").description("회원이 소유한 쿠폰 개수")),
                pathParameters(
                    parameterWithName("memberNo").description("회원 No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCouponCount(1L);
    }



    @Test
    @DisplayName("멤버의 보유 포인트 쿠폰 조회")
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
                    parameterWithName("page").description("조회하려는 페이지"),
                    parameterWithName("size").description("페이지 크기")),
                responseFields(
                    fieldWithPath("pageNumber").description("현재 페이지"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("data.[].couponId").description("쿠폰 ID"),
                    fieldWithPath("data.[].orderCouponId").description("포인트 쿠폰 ID. 포인트 쿠폰은 주문 쿠폰에 속함."),
                    fieldWithPath("data.[].name").description("쿠폰 이름"),
                    fieldWithPath("data.[].amount").description("포인트로 전환가능한 금액")),
                pathParameters(
                    parameterWithName("memberNo").description("회원 No"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).retrievePointCoupons(any(), any());
    }

    @Test
    @DisplayName("회원의 포인트 쿠폰 사용")
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
                    parameterWithName("memberNo").description("회원 No"),
                    parameterWithName("orderCouponId").description("사용자가 소유한 주문 쿠폰(포인트 쿠폰) ID"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).usePointCoupon(any(), any());
    }

    @Test
    @DisplayName("포인트 쿠폰이 사용되었는지 확인 성공")
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
                    parameterWithName("memberNo").description("회원 No"),
                    parameterWithName("orderCouponId").description("사용자가 소유한 주문 쿠폰(포인트 쿠폰) ID"))
            ))
            .andReturn();

        Mockito.verify(couponMemberService).checkUsedPointCoupon(targetId, targetId);
    }
}
