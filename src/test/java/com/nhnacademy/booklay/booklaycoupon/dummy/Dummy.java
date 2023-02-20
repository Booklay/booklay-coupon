package com.nhnacademy.booklay.booklaycoupon.dummy;

import com.nhnacademy.booklay.booklaycoupon.dto.common.MemberInfo;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.couponsetting.CouponSettingCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontemplate.CouponTemplateCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIsBlindRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneIssueToMemberRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.grade.Grade;
import com.nhnacademy.booklay.booklaycoupon.dto.member.request.MemberCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.member.request.MemberUpdateRequest;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponSetting;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponTemplate;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.DeliveryStatusCode;
import com.nhnacademy.booklay.booklaycoupon.entity.Gender;
import com.nhnacademy.booklay.booklaycoupon.entity.Image;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.MemberGrade;
import com.nhnacademy.booklay.booklaycoupon.entity.ObjectFile;
import com.nhnacademy.booklay.booklaycoupon.entity.Order;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderStatusCode;
import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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


    public static OrderProduct getDummyOrderProduct() {
        OrderProduct orderProduct = OrderProduct.builder()
            .order(null)
            .product(null)
            .count(1)
            .price(10000)
            .build();

        ReflectionTestUtils.setField(orderProduct, "id", 1L);

        return orderProduct;
    }

    public static MemberGrade getDummyMemberGrade() {

        MemberGrade memberGrade = MemberGrade.builder()
            .member(getDummyMember())
            .name("white")
            .build();

        ReflectionTestUtils.setField(memberGrade, "id", 1L);

        return memberGrade;
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

    public static DeliveryStatusCode getDummyDeliveryStatusCode() {
        return DeliveryStatusCode.builder()
            .id(1)
            .name("배송중")
            .build();
    }

    public static Order getDummyOrder() {
        Order order = Order.builder()
            .member(Dummy.getDummyMember())
            .orderStatusCode(getDummyOrderStatusCode())
            .productPriceSum(30000L)
            .deliveryPrice(1000L)
            .discountPrice(0L)
            .pointUsePrice(2000L)
            .paymentPrice(31000L)
            .paymentMethod(3L)
            .giftWrappingPrice(4500L)
            .isBlinded(false)
            .build();

        ReflectionTestUtils.setField(order, "id", 1L);
        ReflectionTestUtils.setField(order, "orderedAt", LocalDateTime.now());

        return order;
    }

    public static OrderStatusCode getDummyOrderStatusCode() {
        return OrderStatusCode.builder()
            .id(1L)
            .name("입금대기중")
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

    public static MemberCreateRequest getDummyMemberCreateRequest() {
        MemberCreateRequest memberRequest = new MemberCreateRequest();
        ReflectionTestUtils.setField(memberRequest, "gender", "M");
        ReflectionTestUtils.setField(memberRequest, "memberId", "HoDong");
        ReflectionTestUtils.setField(memberRequest, "password", "$2a$12$5KoVJnK1WF2h4h4T3FmifeO3ZLtAjiayJ783EfvTs7zSIz2GUhnMu");
        ReflectionTestUtils.setField(memberRequest, "nickname", "천하장사");
        ReflectionTestUtils.setField(memberRequest, "name", "강호동");
        ReflectionTestUtils.setField(memberRequest, "birthday", LocalDate.now());
        ReflectionTestUtils.setField(memberRequest, "phoneNo", "01012341234");
        ReflectionTestUtils.setField(memberRequest, "email", "aaaa@gmail.com");

        return memberRequest;
    }

    public static MemberUpdateRequest getDummyMemberUpdateRequest() {
        MemberUpdateRequest memberRequest = new MemberUpdateRequest();
        ReflectionTestUtils.setField(memberRequest, "gender", "M");
        ReflectionTestUtils.setField(memberRequest, "password", "$2a$12$5KoVJnK1WF2h4h4T3FmifeO3ZLtAjiayJ783EfvTs7zSIz2GUhnMu");
        ReflectionTestUtils.setField(memberRequest, "nickname", "천하장사123");
        ReflectionTestUtils.setField(memberRequest, "name", "강호동123");
        ReflectionTestUtils.setField(memberRequest, "birthday", LocalDate.now());
        ReflectionTestUtils.setField(memberRequest, "phoneNo", "01033333333");
        ReflectionTestUtils.setField(memberRequest, "email", "bbbb@gmail.com");

        return memberRequest;
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
        Map<String, String[]> paramMap =  new HashMap<>();
        String[] longStringArray = new String[] {"1"};
        String[] stringArray = new String[] {"test"};
        String[] dateArray = new String[] {"2000, 01, 01"};


        paramMap.put("memberNo",longStringArray);
        paramMap.put("gender",stringArray);
        paramMap.put("memberId",longStringArray);
        paramMap.put("nickname",stringArray);
        paramMap.put("name",stringArray);
        paramMap.put("birthday",dateArray);
        paramMap.put("phoneNo",stringArray);
        paramMap.put("email",stringArray);

        return new MemberInfo(paramMap);
    }

    public static CouponUseRequest getDummyCouponUseRequest() {
        return new CouponUseRequest();
    }

    public static CouponRefundRequest getDummyCouponRefundRequest() {
        return new CouponRefundRequest();
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

    public static Coupon getDummyInvalidCoupon() {
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
}
