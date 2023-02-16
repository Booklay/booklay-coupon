package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.OrderCouponService;
import java.util.List;
import org.junit.jupiter.api.Disabled;
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

@WebMvcTest(CouponOrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponOrderControllerTest {

    @MockBean
    OrderCouponService orderCouponService;

    @Autowired
    CouponOrderController couponOrderController;

    @Autowired
    MockMvc mockMvc;

    String URI_PREFIX = "/admin/coupons/order";
    Long targetId = 1L;

    @Test
    @DisplayName("상품에 적용될 수 있는 주문 쿠폰 조회 성공")
    @Disabled
    void testRetrieveAllCoupons() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponseFromProduct>
            response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(orderCouponService.retrieveCouponPageByMemberNo(targetId, true, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX)
                .param("memberNo", String.valueOf(targetId))
                .param("isDuplicable", "true")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(orderCouponService).retrieveCouponPageByMemberNo(eq(targetId), eq(true), any());
    }
}