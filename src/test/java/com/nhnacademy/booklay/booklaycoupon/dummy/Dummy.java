package com.nhnacademy.booklay.booklaycoupon.dummy;

import com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.response.CouponTypeRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneCheckResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.grade.Grade;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.Gender;
import com.nhnacademy.booklay.booklaycoupon.entity.Image;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.ObjectFile;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

public class Dummy {
    public static Gender getDummyGender() {
        return Gender.builder()
            .id(1L)
            .name("M")
            .build();
    }

    public static Member getDummyMember() {
        Member member = Member.builder()
            .gender(getDummyGender())
            .memberId("dummyMemberId")
            .password("$2a$12$5KoVJnK1WF2h4h4T3FmifeO3ZLtAjiayJ783EfvTs7zSIz2GUhnMu") //1234
            .nickname("메뚜기")
            .name("유재석")
            .birthday(LocalDate.now())
            .phoneNo("01012341234")
            .email("abcd@naver.com")
            .isBlocked(false)
            .build();

        ReflectionTestUtils.setField(member, "memberNo", 1L);
        ReflectionTestUtils.setField(member, "createdAt", LocalDateTime.now());
        return member;
    }

    public static Coupon getDummyCoupon() {
        Coupon coupon = Coupon.builder()
            .couponType(Dummy.getDummyCouponType())
            .name("이달의 쿠폰")
            .amount(5)
            .minimumUseAmount(1000)
            .maximumDiscountAmount(3000)
            .isDuplicatable(false)
            .isLimited(true)
            .build();
        ReflectionTestUtils.setField(coupon, "id", 1L);

        return coupon;
    }

    public static Coupon getDummyCoupon_Point() {
        Coupon coupon = Coupon.builder()
            .couponType(Dummy.getDummyCouponType_Point())
            .name("이달의 쿠폰")
            .amount(5)
            .minimumUseAmount(1000)
            .maximumDiscountAmount(3000)
            .isDuplicatable(false)
            .isLimited(true)
            .build();
        ReflectionTestUtils.setField(coupon, "id", 1L);

        return coupon;
    }

    public static Coupon getDummyCoupon_Order() {
        Coupon coupon = Coupon.builder()
            .couponType(Dummy.getDummyCouponType())
            .name("이달의 쿠폰")
            .amount(5)
            .minimumUseAmount(1000)
            .maximumDiscountAmount(3000)
            .isDuplicatable(false)
            .isLimited(true)
            .build();
        ReflectionTestUtils.setField(coupon, "id", 1L);
        coupon.setCategory(Dummy.getDummyCategory());

        return coupon;
    }

    public static Coupon getDummyCoupon_Product() {
        Coupon coupon = Coupon.builder()
            .couponType(Dummy.getDummyCouponType())
            .name("이달의 쿠폰")
            .amount(5)
            .minimumUseAmount(1000)
            .maximumDiscountAmount(3000)
            .isDuplicatable(false)
            .isLimited(true)
            .build();
        ReflectionTestUtils.setField(coupon, "id", 1L);
        coupon.setProduct(Dummy.getDummyProduct());

        return coupon;
    }

    public static Category getDummyCategory() {

        Category allProduct = Category.builder()
            .id(1L)
            .parent(null)
            .name("전체 상품")
            .depth(0L)
            .isExposure(true)
            .build();

        return Category.builder()
            .id(101L)
            .parent(allProduct)
            .name("국내도서")
            .depth(allProduct.getDepth() + 1)
            .isExposure(true)
            .build();
    }

    public static CouponType getDummyCouponType() {
        return CouponType.builder()
            .id(1L)
            .name("정율")
            .build();
    }

    public static CouponType getDummyCouponType_Point() {
        return CouponType.builder()
            .id(1L)
            .name("포인트")
            .build();
    }

    public static CouponCURequest getDummyOrderCouponCURequest() {
        CouponCURequest couponRequest = new CouponCURequest();

        ReflectionTestUtils.setField(couponRequest, "name", "이달의 쿠폰");
        ReflectionTestUtils.setField(couponRequest, "typeCode", 1L);
        ReflectionTestUtils.setField(couponRequest, "amount", 5);
        ReflectionTestUtils.setField(couponRequest, "isOrderCoupon", true);
        ReflectionTestUtils.setField(couponRequest, "applyItemId", Dummy.getDummyCategory().getId());
        ReflectionTestUtils.setField(couponRequest, "minimumUseAmount", 1000);
        ReflectionTestUtils.setField(couponRequest, "maximumDiscountAmount", 5000);
        ReflectionTestUtils.setField(couponRequest, "isDuplicatable", true);
        ReflectionTestUtils.setField(couponRequest, "isLimited", true);

        return couponRequest;
    }

    public static CouponCURequest getDummyOrderCouponCURequest_NoApplyItem() {
        CouponCURequest couponRequest = new CouponCURequest();

        ReflectionTestUtils.setField(couponRequest, "name", "이달의 쿠폰");
        ReflectionTestUtils.setField(couponRequest, "typeCode", 1L);
        ReflectionTestUtils.setField(couponRequest, "amount", 5);
        ReflectionTestUtils.setField(couponRequest, "isOrderCoupon", true);
        ReflectionTestUtils.setField(couponRequest, "minimumUseAmount", 1000);
        ReflectionTestUtils.setField(couponRequest, "maximumDiscountAmount", 5000);
        ReflectionTestUtils.setField(couponRequest, "isDuplicatable", true);
        ReflectionTestUtils.setField(couponRequest, "isLimited", true);

        return couponRequest;
    }

    public static CouponCURequest getDummyOrderCouponCURequest_NotOrderCoupon() {
        CouponCURequest couponRequest = new CouponCURequest();

        ReflectionTestUtils.setField(couponRequest, "name", "이달의 쿠폰");
        ReflectionTestUtils.setField(couponRequest, "typeCode", 1L);
        ReflectionTestUtils.setField(couponRequest, "amount", 5);
        ReflectionTestUtils.setField(couponRequest, "isOrderCoupon", false);
        ReflectionTestUtils.setField(couponRequest, "applyItemId", Dummy.getDummyCategory().getId());
        ReflectionTestUtils.setField(couponRequest, "minimumUseAmount", 1000);
        ReflectionTestUtils.setField(couponRequest, "maximumDiscountAmount", 5000);
        ReflectionTestUtils.setField(couponRequest, "isDuplicatable", true);
        ReflectionTestUtils.setField(couponRequest, "isLimited", true);

        return couponRequest;
    }

    public static CouponTypeCURequest getDummyCouponTypeCURequest() {
        CouponTypeCURequest couponTypeRequest = new CouponTypeCURequest();
        ReflectionTestUtils.setField(couponTypeRequest, "id", 1L);
        ReflectionTestUtils.setField(couponTypeRequest, "name", "정율");

        return couponTypeRequest;
    }

    public static CouponRetrieveResponse getDummyCouponRetrieveResponse() {
        return CouponRetrieveResponse.fromEntity(Dummy.getDummyCoupon());
    }

    public static OrderCoupon getDummyOrderCoupon() {
        OrderCoupon orderCoupon = OrderCoupon.builder()
            .coupon(getDummyCoupon())
            .code(CodeUtils.getOrderCouponCode())
            .isUsed(false)
            .build();

        ReflectionTestUtils.setField(orderCoupon, "id", 1L);

        return orderCoupon;
    }

    public static Product getDummyProduct() {
        return Product.builder()
            .price(10000L)
            .pointMethod(true)
            .pointRate(5L)
            .title("test title")
            .shortDescription("test short description")
            .longDescription("test long description")
            .isSelling(true)
            .build();
    }

    public static CouponSetting getCouponSetting(){
        CouponSetting couponSetting = CouponSetting.builder()
            .couponTemplateNo(1L)
            .settingType(1)
            .memberGrade(1L)
            .build();
        ReflectionTestUtils.setField(couponSetting, "id", 1L);
        return couponSetting;
    }

    public static CouponTemplate getCouponTemplate(){
        CouponTemplate couponTemplate = CouponTemplate.builder()
            .image(Image.builder().id(1L).address("dummy").build())
            .typeCode(1L)
            .isOrderCoupon(true)
            .applyItemId(1L)
            .name("더미 주문쿠폰")
            .amount(100)
            .minimumUseAmount(100)
            .maximumDiscountAmount(100)
            .issuingDeadLine(LocalDateTime.now())
            .validateTerm(7)
            .isDuplicatable(false)
            .build();
        ReflectionTestUtils.setField(couponTemplate, "id", 1L);
        return couponTemplate;

    }

    public static CouponSettingCURequest getCouponSettingCURequest(){
        return new CouponSettingCURequest(1, 1L, 1L);
    }

    public static CouponTemplateCURequest getCouponTemplateCURequest(){
        CouponTemplateCURequest couponTemplateCURequest = new CouponTemplateCURequest("더미상품쿠폰", 1L, 100
            , true, 1L, 100, 100
            , LocalDateTime.now(), 7, false, "default");

        return couponTemplateCURequest;
    }

    public static ProductCoupon getDummyProductCoupon() {
        ProductCoupon productCoupon = ProductCoupon.builder()
            .coupon(getDummyCoupon())
            .code(CodeUtils.getProductCouponCode())
            .build();

        ReflectionTestUtils.setField(productCoupon, "id", 1L);

        return productCoupon;
    }

    public static CouponZoneCreateRequest getDummyCouponZoneCreateRequest() {
        return new CouponZoneCreateRequest(1L, "description", Grade.WHITE.getKorGrade(), LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), false);
    }

    public static CouponZoneIsBlindRequest getDummyCouponZoneIsBlindRequest() {
        return new CouponZoneIsBlindRequest(true);
    }

    public static MemberInfo getDummyMemberInfo() {
        String[] longStringArray = new String[] {"1"};
        String[] stringArray = new String[] {"test"};
        String[] dateArray = new String[] {"2000, 01, 01"};
        MemberInfo memberInfo= new MemberInfo();

        ReflectionTestUtils.setField(memberInfo, "memberNo",longStringArray);
        ReflectionTestUtils.setField(memberInfo, "gender",stringArray);
        ReflectionTestUtils.setField(memberInfo, "memberId",longStringArray);
        ReflectionTestUtils.setField(memberInfo, "nickname",stringArray);
        ReflectionTestUtils.setField(memberInfo, "name",stringArray);
        ReflectionTestUtils.setField(memberInfo, "birthday",dateArray);
        ReflectionTestUtils.setField(memberInfo, "phoneNo",stringArray);
        ReflectionTestUtils.setField(memberInfo, "email",stringArray);

        return memberInfo;
    }

    public static CouponUsingDto getDummyCouponUsingDto() {
        CouponUsingDto couponUsingDto = new CouponUsingDto();
        ReflectionTestUtils.setField(couponUsingDto, "couponCode", "code");
        ReflectionTestUtils.setField(couponUsingDto, "specifiedCouponNo", 1L);
        ReflectionTestUtils.setField(couponUsingDto, "usedTargetNo", 1L);

        return couponUsingDto;
    }

    public static CouponUseRequest getDummyCouponUseRequest() {
        CouponUseRequest request = new CouponUseRequest();
        ReflectionTestUtils.setField(request, "productCouponList", List.of(Dummy.getDummyCouponUsingDto()));
        ReflectionTestUtils.setField(request, "categoryCouponList", List.of(Dummy.getDummyCouponUsingDto()));

        return request;
    }

    public static CouponRefundRequest getDummyCouponRefundRequest() {
        CouponRefundRequest request = new CouponRefundRequest();
        ReflectionTestUtils.setField(request, "orderProductNoList", List.of(1L, 2L));
        ReflectionTestUtils.setField(request, "orderNo", 1L);

        return request;
    }

    public static CouponZoneIssueToMemberRequest getDummyCouponZoneIssueToMemberRequest() {
        return new CouponZoneIssueToMemberRequest(1L, 1L);
    }

    public static ObjectFile getDummyObjectFile() {
        ObjectFile objectFile = new ObjectFile("test", "test");
        ReflectionTestUtils.setField(objectFile, "id", 1L);

        return objectFile;
    }

    public static MemberCouponRetrieveResponse getDummyMemberCouponRetrieveResponse() {
        return new MemberCouponRetrieveResponse("test", 5, "정률", 1L, 5000, 5000, LocalDateTime.now(), true);
    }

    public static CouponDetailRetrieveResponse getDummyCouponDetailRetrieveResponse() {
        return CouponDetailRetrieveResponse.builder()
            .id(1L)
            .name("이달의 쿠폰")
            .typeName("정액")
            .amount(5)
            .applyItemId(1L)
            .itemName("불편한 편의점")
            .minimumUseAmount(5000)
            .maximumDiscountAmount(10000)
            .isDuplicatable(false)
            .isLimited(true)
            .objectFileId(1L)
            .isOrderCoupon(false)
            .build();
    }

    public static CouponHistoryRetrieveResponse getDummyCouponHistoryRetrieveResponse() {
        return new CouponHistoryRetrieveResponse(1L, "code", "이달의 쿠폰", "memberID", LocalDateTime.now(), LocalDateTime.now());
    }

    public static CouponUsedHistoryResponse getDummyCouponUsedHistoryResponse() {
        return new CouponUsedHistoryResponse("memberId", "이달의 쿠폰", 3000L, LocalDateTime.now(), LocalDateTime.now());
    }

    public static PointCouponRetrieveResponse getDummyPointCouponRetrieveResponse() {
        return new PointCouponRetrieveResponse(1L, 1L, "이달의 포인트 쿠폰", 1000);
    }

    public static CouponRetrieveResponseFromProduct getDummyCouponRetrieveResponseFromProduct() {
        return new CouponRetrieveResponseFromProduct("code", Dummy.getDummyCoupon());
    }

    public static CouponTypeRetrieveResponse getDummyCouponTypeRetrieveResponse() {
        return new CouponTypeRetrieveResponse(1L, "정률");
    }

    public static CouponZoneResponse getDummyCouponZoneResponse() {
        return new CouponZoneResponse(1L, 1L, 1L, "이달의 쿠폰", "선착순 300명!", "화이트", LocalDateTime.of(2023, 1, 1, 12, 0), LocalDateTime.of(2023, 1, 25, 12, 0), LocalDateTime.of(2023, 1, 28, 12, 0), false);
    }

    public static CouponZoneCheckResponse getDummyCouponZoneCheckResponse() {
        return new CouponZoneCheckResponse(LocalDateTime.of(2023, 1, 1, 12, 0), LocalDateTime.of(2023, 1, 25, 12, 0), "화이트");
    }
}
