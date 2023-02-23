package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponGeneralService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponRestControllerTest {

    @MockBean
    CouponGeneralService couponGeneralService;

    @Autowired
    CouponRestController couponRestController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String DOC_PREFIX = "coupons";
    String URI_PREFIX = "/" + DOC_PREFIX;

    @Test
    void testRetrieveCouponByCouponCode() throws Exception {
        // given

        // when
        when(couponGeneralService.retrieveCouponByCouponCode("couponCode")).thenReturn(Dummy.getDummyCouponRetrieveResponseFromProduct());

        // then
        mockMvc.perform(get(URI_PREFIX + "/code")
                .queryParam("couponCode", "couponCode"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("couponCode").description("조회하려는 쿠폰의 코드")),
                responseFields(
                    fieldWithPath("id").description("쿠폰 ID"),
                    fieldWithPath("name").description("쿠폰 이름"),
                    fieldWithPath("typeName").description("쿠폰 타입 이름"),
                    fieldWithPath("amount").description("할인량"),
                    fieldWithPath("minimumUseAmount").description("최소 사용 금액"),
                    fieldWithPath("maximumDiscountAmount").description("최대 할인 금액"),
                    fieldWithPath("isLimited").description("수량 제한 여부"),
                    fieldWithPath("couponCode").description("쿠폰 코드"),
                    fieldWithPath("categoryNo").description("적용 카테고리 ID"),
                    fieldWithPath("productNo").description("적용 상품 ID"))
            ))
            .andReturn();

        Mockito.verify(couponGeneralService).retrieveCouponByCouponCode(any());
    }

    @Test
    void testRetrieveCouponByCouponCodeList() throws Exception {
        // given

        // when
        when(couponGeneralService.retrieveCouponByCouponCodeList(anyList(), any())).thenReturn(List.of(Dummy.getDummyCouponRetrieveResponseFromProduct()));

        // then
        mockMvc.perform(get(URI_PREFIX + "/codes")
                        .queryParam("couponCodeList", "list")
                        .queryParam("member_info_memberNo", "1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("couponCodeList").description("조회하려는 쿠폰의 코드 리스트"),
                    parameterWithName("member_info_memberNo").description("회원 No")),
                responseFields(
                    fieldWithPath("[].id").description("쿠폰 ID"),
                    fieldWithPath("[].name").description("쿠폰 이름"),
                    fieldWithPath("[].typeName").description("쿠폰 타입 이름"),
                    fieldWithPath("[].amount").description("할인량"),
                    fieldWithPath("[].minimumUseAmount").description("최소 사용 금액"),
                    fieldWithPath("[].maximumDiscountAmount").description("최대 할인 금액"),
                    fieldWithPath("[].isLimited").description("수량 제한 여부"),
                    fieldWithPath("[].couponCode").description("쿠폰 코드"),
                    fieldWithPath("[].categoryNo").description("적용 카테고리 ID"),
                    fieldWithPath("[].productNo").description("적용 상품 ID"))
            ))
            .andReturn();

        Mockito.verify(couponGeneralService).retrieveCouponByCouponCodeList(any(), any());
    }

    @Test
    void testUseCoupons() throws Exception {
        // given
        CouponUseRequest request = Dummy.getDummyCouponUseRequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/using")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("productCouponList").description("상품 쿠폰 리스트 객체"),
                    fieldWithPath("productCouponList.[].couponCode").description("쿠폰 코드"),
                    fieldWithPath("productCouponList.[].specifiedCouponNo").description("쿠폰 ID"),
                    fieldWithPath("productCouponList.[].usedTargetNo").description("적용 상품 ID"),
                    fieldWithPath("categoryCouponList").description("주문 쿠폰 리스트"),
                    fieldWithPath("categoryCouponList.[].couponCode").description("쿠폰 ID"),
                    fieldWithPath("categoryCouponList.[].specifiedCouponNo").description("쿠폰 ID"),
                    fieldWithPath("categoryCouponList.[].usedTargetNo").description("적용 카테고리 ID"))
            ))
            .andReturn();

        Mockito.verify(couponGeneralService).couponUsing(any());
    }

    @Test
    void testRefundCoupons() throws Exception {
        // given
        CouponRefundRequest request = Dummy.getDummyCouponRefundRequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/refund")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("orderProductNoList").description("주문 상품 번호 리스트"),
                    fieldWithPath("orderNo").description("주문 번호"))
            ))
            .andReturn();

        Mockito.verify(couponGeneralService).couponRefund(any());
    }
}