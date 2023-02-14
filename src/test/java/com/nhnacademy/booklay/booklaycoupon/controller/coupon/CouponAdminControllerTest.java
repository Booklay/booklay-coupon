package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminService;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponIssueService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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

/**
 *
 * @author 김승혜
 */

@WebMvcTest(CouponAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponAdminControllerTest {

    @MockBean
    CouponAdminService couponAdminService;

    @MockBean
    CouponIssueService couponIssueService;

    @Autowired
    CouponAdminController couponAdminController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String URI_PREFIX = "/admin/coupons";
    Long targetId = 1L;

    @Test
    @DisplayName("모든 쿠폰 조회 성공 테스트")
    void testRetrieveAllCoupons() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponRetrieveResponse> response = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponAdminService.retrieveAllCoupons(any())).thenReturn(response);

        // then
        mockMvc.perform(get(URI_PREFIX)
                .queryParam("page", "0")
                .queryParam("size", "10")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService).retrieveAllCoupons(any());
    }

    @Test
    @DisplayName("쿠폰 생성 성공 테스트")
    void testCreateCoupon() throws Exception {

        // given
        CouponCURequest couponRequest = Dummy.getDummyOrderCouponCURequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX)
            .content(objectMapper.writeValueAsString(couponRequest))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService).createCoupon(any());
    }

    @Test
    @DisplayName("쿠폰 단건 조회 성공 테스트")
    void testRetrieveCouponDetail() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(get(URI_PREFIX + "/" + targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService).retrieveCoupon(targetId);
    }

    @Test
    @DisplayName("존재하지 않는 쿠폰 조회 시 실패 테스트")
    void testRetrieveCouponDetailFail() throws Exception {

        // given

        // when
        when(couponAdminService.retrieveCoupon(targetId)).thenThrow(NotFoundException.class);

        // then
        mockMvc.perform(get(URI_PREFIX + "/" + targetId)
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is4xxClientError())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).retrieveCoupon(targetId);
    }

    @Test
    @DisplayName("쿠폰 정보 수정 성공 테스트")
    void testUpdateCoupon() throws Exception {

        // given
        CouponCURequest couponRequest = Dummy.getDummyOrderCouponCURequest();

        // when


        // then
        mockMvc.perform(put(URI_PREFIX + "/" + targetId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponRequest)))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).updateCoupon(eq(targetId), any());
    }

    @Test
    @DisplayName("쿠폰 이미지 수정 성공 테스트")
    void testUpdateCouponImage() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(put(URI_PREFIX + "/image/" + targetId + "/" + targetId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).updateCouponImage(targetId, targetId);
    }

    @Test
    @DisplayName("쿠폰 이미지 삭제 성공 테스트")
    void testDeleteCouponImage() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(put(URI_PREFIX + "/image/" + targetId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService, times(1)).deleteCouponImage(targetId);
    }

    @Test
    @DisplayName("쿠폰 삭제 테스트")
    void testDeleteCoupon() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(delete(URI_PREFIX + "/1"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService).deleteCoupon(1L);
    }

    @Test
    @Disabled
    @DisplayName("관리자가 사용자에게 쿠폰 발급")
    void testIssueCouponToMember() throws Exception {

        // given
        CouponIssueToMemberRequest couponRequest = new CouponIssueToMemberRequest(1L, 1L, LocalDateTime.of(2000,02,02,11,11,11));

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/members/issue")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponIssueService).issueCouponToMember(any());
    }

    @Test
    @DisplayName("관리자가 쿠폰 수량 발급")
    void testIssueCoupon() throws Exception {

        // given
        CouponIssueRequest couponRequest = new CouponIssueRequest(1L, 1);

        // when

        // then
        mockMvc.perform(post(URI_PREFIX + "/issue")
                .content(objectMapper.writeValueAsString(couponRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponIssueService).issueCoupon(any());
    }

    @Test
    @DisplayName("관리자의 발급 내역 조회")
    void testRetrieveCouponIssueHistory() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponHistoryRetrieveResponse> couponRetrieveResponses = new PageImpl<>(List.of(), pageRequest, 1);

        // when
        when(couponAdminService.retrieveIssuedCoupons(any())).thenReturn(couponRetrieveResponses);

        // then
        mockMvc.perform(get(URI_PREFIX + "/issue-history")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(couponAdminService).retrieveIssuedCoupons(any());
    }

    @Test
    @DisplayName("사용된 쿠폰 조회 테스트")
    void testRetrieveCouponUsageHistory() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(get(URI_PREFIX + "/history"))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

    }
}