package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponMemberService;
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

@WebMvcTest(CouponMemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponMemberControllerTest {

    @MockBean
    CouponMemberService couponMemberService;

    @Autowired
    CouponMemberController couponMemberController;

    @Autowired
    MockMvc mockMvc;

    private static final String URI_PREFIX = "/members/1/coupons";

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("회원이 소유한 쿠폰 조회 성공.")
    void testRetrieveCouponsByMember() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<MemberCouponRetrieveResponse> response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponMemberService.retrieveCoupons(1L, pageRequest)).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCoupons(any(), any());
    }

    @Test
    @DisplayName("회원이 소유한 쿠폰의 개수 조회 성공.")
    void testRetrieveCouponCountByMember() throws Exception {
        // given

        // when
        when(couponMemberService.retrieveCouponCount(1L)).thenReturn(1);

        // then
        mockMvc.perform(get(URI_PREFIX + "/count")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponMemberService).retrieveCouponCount(1L);
    }



    @Test
    @DisplayName("멤버의 보유 포인트 쿠폰 조회")
    void testRetrievePointCoupons() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<PointCouponRetrieveResponse> couponRetrieveResponses = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponMemberService.retrievePointCoupons(1L, pageRequest)).thenReturn(couponRetrieveResponses);

        // then
        mockMvc.perform(get(URI_PREFIX + "/point")
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponMemberService).retrievePointCoupons(any(), any());
    }

    @Test
    @DisplayName("회원의 포인트 쿠폰 사용")
    void testUsePointCoupon() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/point/1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponMemberService).usePointCoupon(any(), any());
    }
}