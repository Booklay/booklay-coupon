package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.OrderCouponService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.ProductCouponService;
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
@WebMvcTest(CouponProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponProductControllerTest {

    @MockBean
    ProductCouponService productCouponService;

    @Autowired
    CouponProductController couponProductController;

    @Autowired
    MockMvc mockMvc;

    String DOC_PREFIX = "admin/coupons/product";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    @DisplayName("????????? ????????? ??? ?????? ?????? ?????? ??????")
    void retrieveAllCoupons() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponseFromProduct>
            response = new PageImpl<>(List.of(Dummy.getDummyCouponRetrieveResponseFromProduct()), pageRequest, 1);

        // when
        when(productCouponService.retrieveCouponPageByMemberNoAndProductNo(null, targetId, true, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get(URI_PREFIX + "/{productNo}", targetId)
                .param("memberNo", targetId.toString())
                .param("isDuplicable", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("memberNo").description("?????? No"),
                    parameterWithName("isDuplicable").description("?????? ?????? ?????? ??????")
                ),
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
                    fieldWithPath("data.[].isLimited").description("?????? ?????? ??????"),
                    fieldWithPath("data.[].couponCode").description("?????? ??????"),
                    fieldWithPath("data.[].categoryNo").description("?????? ???????????? ID"),
                    fieldWithPath("data.[].productNo").description("?????? ?????? ID"))
            ))
            .andReturn();

        Mockito.verify(productCouponService).retrieveCouponPageByMemberNoAndProductNo(any(), eq(targetId),eq(true), any());
    }
}