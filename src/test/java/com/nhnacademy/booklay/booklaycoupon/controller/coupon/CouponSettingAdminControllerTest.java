package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.couponSetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.service.couponSetting.CouponSettingService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CouponSettingAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponSettingAdminControllerTest {
    @MockBean
    CouponSettingService couponSettingService;

    @Autowired
    CouponSettingAdminController couponSettingAdminController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    CouponSetting couponSetting;
    List<CouponSetting> couponSettingList;
    CouponSettingCURequest couponSettingCURequest;


    private static final String URI_PREFIX = "/admin/couponSettings";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        couponSetting = Dummy.getCouponSetting();
        couponSettingList = List.of(couponSetting);
        couponSettingCURequest = Dummy.getCouponSettingCURequest();

    }
    @Test
    void createCouponSetting() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(post(URI_PREFIX)
            .content(objectMapper.writeValueAsString(couponSettingCURequest))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isCreated());
        then(couponSettingService).should(times(1)).createSetting(any());
    }

    @Test
    void retrieveAllCouponSettings() throws Exception {
        //given

        //mocking
        given(couponSettingService.retrieveAllSetting()).willReturn(couponSettingList);

        //then
        mockMvc.perform(get(URI_PREFIX))
            .andExpect(status().isOk())
            .andExpect(result -> result.getResponse().getContentAsString().equals(objectMapper.writeValueAsString(couponSettingList)))
            .andReturn();
        then(couponSettingService).should(times(1)).retrieveAllSetting();
    }

    @Test
    void retrieveAllCouponSetting_byPage() throws Exception {
        //given
        Page<CouponSetting> page = new PageImpl<>(couponSettingList);

        //mocking
        given(couponSettingService.retrieveAllSettingPage(any())).willReturn(page);

        //then
        mockMvc.perform(get(URI_PREFIX+"/pages"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['data']").isArray())
            .andReturn();
        then(couponSettingService).should(times(1)).retrieveAllSettingPage(any());
    }
    @Test
    void retrieveAllCouponSettings_bySettingType() throws Exception {
        //given

        //mocking
        given(couponSettingService.retrieveSettings(any())).willReturn(couponSettingList);
        //then
        mockMvc.perform(get(URI_PREFIX+"/type/"+couponSettingCURequest.getSettingType()))
            .andExpect(status().isOk())
            .andExpect(result -> result.getResponse().getContentAsString().equals(objectMapper.writeValueAsString(couponSettingList)))
            .andReturn();
        then(couponSettingService).should(times(1)).retrieveSettings(any());
    }
    @Test
    void retrieveAllCouponSettings_byPageAndSettingType() throws Exception {
        //given
        Page<CouponSetting> page = new PageImpl<>(couponSettingList);

        //mocking
        given(couponSettingService.retrieveSettingsPage(any(), any())).willReturn(page);

        //then
        mockMvc.perform(get(URI_PREFIX+"/pages/"+couponSetting.getSettingType()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['data']").isArray())
            .andReturn();
        then(couponSettingService).should(times(1)).retrieveSettingsPage(any(), any());
    }
    @Test
    void retrieveCouponSettingDetail() throws Exception {
        //given
        given(couponSettingService.retrieveSetting(any())).willReturn(couponSetting);

        //when
        ResultActions result = mockMvc.perform(get(URI_PREFIX + "/" + couponSetting.getId())
            .accept(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$['id']").value(couponSetting.getId()))
            .andExpect(jsonPath("$['settingType']").value(couponSetting.getSettingType()));
        then(couponSettingService).should(times(1)).retrieveSetting(any());
    }

    @Test
    void updateCouponSetting() throws Exception {
        //given
        given(couponSettingService.updateSetting(any(), any())).willReturn(couponSetting);
        //when
        ResultActions result = mockMvc.perform(put(URI_PREFIX+"/"+couponSetting.getId())
            .content(objectMapper.writeValueAsString(couponSettingCURequest))
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpect(status().isOk());
        then(couponSettingService).should(times(1)).updateSetting(any(), any());
    }

    @Test
    void deleteCouponSetting() throws Exception {
        //given

        //when
        ResultActions result = mockMvc.perform(delete(URI_PREFIX+"/"+couponSetting.getId()));

        //then
        result.andExpect(status().isOk());
        then(couponSettingService).should(times(1)).deleteSetting(any());
    }
}