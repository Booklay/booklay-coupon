package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponWelcomeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponWelcomeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponWelcomeControllerTest {

    @MockBean
    CouponWelcomeService welcomeService;

    @Autowired
    CouponWelcomeController welcomeController;

    @Autowired
    MockMvc mockMvc;

    String DOC_PREFIX = "welcome/{memberNo}";
    String URI_PREFIX = "/" + DOC_PREFIX;
    Long targetId = 1L;

    @Test
    void issueWelcomeCouponToMember() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.post(URI_PREFIX, targetId))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("memberNo").description("회원 가입 쿠폰 발급 대상 회원 ID"))
            ))
            .andReturn();

        Mockito.verify(welcomeService).issueWelcomeCoupon(targetId);

    }
}