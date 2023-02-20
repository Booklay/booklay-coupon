package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponGeneralService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
    String URI_PREFIX = "/coupons";
    Long targetId = 1L;

    @Test
    void testRetrieveCouponByCouponCode() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get(URI_PREFIX + "/code")
                .queryParam("couponCode", "couponCode"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponGeneralService).retrieveCouponByCouponCode(any());
    }

    @Test
    void testRetrieveCouponByCouponCodeList() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get(URI_PREFIX + "/codes")
                .queryParam("couponCodeList", "list"))
            .andExpect(status().isOk())
            .andDo(print())
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
            .andReturn();

        Mockito.verify(couponGeneralService).couponUsing(any(), any());
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
            .andReturn();

        Mockito.verify(couponGeneralService).couponRefund(any(), any());
    }
}