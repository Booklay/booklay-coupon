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
import com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.OrderCouponService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponOrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Import(AnnotationAwareAspectJAutoProxyCreator.class)
class CouponOrderControllerTest {

    @MockBean
    OrderCouponService orderCouponService;

    @Autowired
    CouponOrderController couponOrderController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String DOC_PREFIX = "admin/coupons/order";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    @DisplayName("상품에 적용될 수 있는 주문 쿠폰 조회 성공")
    @Disabled
    void testRetrieveAllCoupons() throws Exception {

        // given
        MemberInfo memberInfo = Dummy.getDummyMemberInfo();
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponseFromProduct>
            response = new PageImpl<>(List.of(Dummy.getDummyCouponRetrieveResponseFromProduct()), pageRequest, 1);

        // when
        when(orderCouponService.retrieveCouponPageByMemberNo(targetId, true, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX)
                .content(objectMapper.writeValueAsString(memberInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .queryParam("isDuplicable", "true"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(orderCouponService).retrieveCouponPageByMemberNo(eq(targetId), eq(true), any());
    }
}