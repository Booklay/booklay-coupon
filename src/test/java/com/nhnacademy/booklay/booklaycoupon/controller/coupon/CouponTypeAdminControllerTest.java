package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.response.CouponTypeRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.service.coupontype.CouponTypeService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * ?????? ?????? ??????, ??????, ??????, ?????? ???????????? ?????????.
 *
 * @author ?????????
 */

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(CouponTypeAdminController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CouponTypeAdminControllerTest {

    @MockBean
    CouponTypeService couponTypeService;

    @Autowired
    CouponTypeAdminController couponTypeAdminController;

    @Autowired
    MockMvc mockMvc;

    String DOC_PREFIX = "admin/couponTypes";
    String URI_PREFIX = "/" + DOC_PREFIX;
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????? ?????????")
    void retrieveAllCouponTypes() throws Exception {

        // given
        PageRequest pageRequest = PageRequest.of(0,10);
        PageImpl<CouponTypeRetrieveResponse>
            couponTypePage = new PageImpl<>(List.of(Dummy.getDummyCouponTypeRetrieveResponse()), pageRequest, 1);

        // when
        when(couponTypeService.retrieveAllCouponTypes(any())).thenReturn(couponTypePage);

        // then
        mockMvc.perform(get(URI_PREFIX)
                .queryParam("page", "0")
                .queryParam("size", "10")
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("pageNumber").description("?????? ?????????"),
                    fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("????????? ??????"),
                    fieldWithPath("totalPages").description("??? ?????????"),
                    fieldWithPath("data.[].id").description("?????? ?????? ID"),
                    fieldWithPath("data.[].name").description("?????? ?????????"))
            ))
            .andReturn();

        Mockito.verify(couponTypeService).retrieveAllCouponTypes(any());
    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ?????????")
    void createCouponType() throws Exception {

        // given
        CouponTypeCURequest couponTypeRequest = Dummy.getDummyCouponTypeCURequest();

        // when

        // then
        mockMvc.perform(post(URI_PREFIX)
                .content(objectMapper.writeValueAsString(couponTypeRequest))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").description("?????? ?????? ID"),
                    fieldWithPath("name").description("?????? ?????????"))
            ))
            .andReturn();

        Mockito.verify(couponTypeService).createCouponType(any());
    }

    @Test
    void updateCouponType() throws Exception {

        // given
        CouponTypeCURequest couponRequest = Dummy.getDummyCouponTypeCURequest();

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.put(URI_PREFIX + "/{couponTypeId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(couponRequest)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("id").description("?????? ?????? ID"),
                    fieldWithPath("name").description("?????? ?????????")),
                pathParameters(
                    parameterWithName("couponTypeId").description("?????? ?????? ?????? ?????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponTypeService, times(1)).updateCouponType(eq(1L), any());
    }

    @Test
    void deleteCouponType() throws Exception {

        // given

        // when

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.delete(URI_PREFIX + "/{couponTypeId}", 1))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document(DOC_PREFIX + "/{methodName}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("couponTypeId").description("?????? ?????? ?????? ?????? ID"))
            ))
            .andReturn();

        Mockito.verify(couponTypeService).deleteCouponType(1L);
    }
}