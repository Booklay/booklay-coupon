package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CouponProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponProductControllerTest {

    @MockBean
    ProductCouponService productCouponService;

    @Autowired
    CouponProductController couponProductController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String URI_PREFIX = "/admin/coupons/product";
    Long targetId = 1L;

    @Test
    @DisplayName("상품에 적용될 수 있는 상품 쿠폰 조회")
    void retrieveAllCoupons() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponseFromProduct>
            response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(productCouponService.retrieveCouponPageByMemberNoAndProductNo(null, targetId, true, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/" + targetId)
                .param("memberNo", targetId.toString())
                .param("isDuplicable", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(productCouponService).retrieveCouponPageByMemberNoAndProductNo(any(), eq(targetId),eq(true), any());
    }
}