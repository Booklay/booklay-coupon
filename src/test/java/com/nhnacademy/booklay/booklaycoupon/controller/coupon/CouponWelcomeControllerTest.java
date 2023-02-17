package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponWelcomeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CouponWelcomeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponWelcomeControllerTest {

    @MockBean
    CouponWelcomeService welcomeService;

    @Autowired
    CouponWelcomeController welcomeController;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    String URI_PREFIX = "/welcome/1";
    Long targetId = 1L;

    @Test
    void issueWelcomeCouponToMember() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(post(URI_PREFIX))
            .andExpect(status().isOk())
            .andDo(print())
            .andReturn();

        Mockito.verify(welcomeService).issueWelcomeCoupon(targetId);

    }
}