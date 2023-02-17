package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
import com.nhnacademy.booklay.booklaycoupon.service.couponzone.CouponZoneService;
import java.util.List;
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

@WebMvcTest(CouponZoneController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponZoneControllerTest {

    @MockBean
    CouponZoneService couponZoneService;

    @Autowired
    CouponZoneController couponZoneController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String URI_PREFIX = "/member/coupon-zone";
    Long targetId = 1L;

    @Test
    void testRetrieveCouponZoneLimited() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneLimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/limited"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneLimited(any());
    }

    @Test
    void testRetrieveCouponZoneUnlimited() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneUnlimited(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/unlimited"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneUnlimited(any());
    }

    @Test
    void testRetrieveCouponZoneGraded() throws Exception {
        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponZoneResponse> response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponZoneService.retrieveCouponZoneGraded(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX + "/graded"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneGraded(any());
    }

    @Test
    void testRetrieveCouponZoneInform() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(get(URI_PREFIX + "/1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponZoneService).retrieveCouponZoneInform(1L);
    }

    @Test
    void issueNoLimitCoupon() throws Exception {
        // given
        CouponZoneIssueToMemberRequest request = Dummy.getDummyCouponZoneIssueToMemberRequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/no-limit")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isForbidden())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponZoneService).issueNoLimitCoupon(any(), any());
    }
}