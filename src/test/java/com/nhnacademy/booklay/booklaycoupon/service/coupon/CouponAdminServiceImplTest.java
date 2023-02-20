package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.repository.CategoryRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.ProductRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTypeRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.objectfile.ObjectFileRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponAdminServiceImplTest {

    @InjectMocks
    private CouponAdminServiceImpl couponAdminService;

    @Mock
    private GetCouponService couponService;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private OrderCouponRepository orderCouponRepository;

    @Mock
    private ProductCouponRepository productCouponRepository;

    @Mock
    private ObjectFileRepository fileRepository;

    Coupon coupon;
    Coupon pointCoupon;
    Coupon orderCoupon;
    Coupon productCoupon;
    List<Coupon> couponList;
    CouponType couponType;
    CouponCURequest couponCURequest;
    Category category;

    @BeforeEach
    void setUp() {
        coupon = Dummy.getDummyCoupon();
        pointCoupon = Dummy.getDummyCoupon_Point();
        orderCoupon = Dummy.getDummyCoupon_Order();
        productCoupon = Dummy.getDummyCoupon_Product();

        couponList = List.of(coupon);
        couponType = Dummy.getDummyCouponType();
        couponCURequest = Dummy.getDummyOrderCouponCURequest();
        category = Dummy.getDummyCategory();
    }

    @Test
    @DisplayName("쿠폰 생성 테스트")
    void testCreateCoupon() {

        // given
        given(couponTypeRepository.findById(couponCURequest.getTypeCode())).willReturn(
            Optional.ofNullable(Dummy.getDummyCouponType()));

        given(categoryRepository.findById(couponCURequest.getApplyItemId())).willReturn(
            Optional.ofNullable(Dummy.getDummyCategory()));

        // when
        couponAdminService.createCoupon(couponCURequest);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 생성 시 쿠폰의 applyItem 없을 때 테스트")
    void testCreateCoupon_NoApplyItem() {

        // given
        CouponCURequest request = Dummy.getDummyOrderCouponCURequest_NoApplyItem();
        given(couponTypeRepository.findById(request.getTypeCode())).willReturn(
            Optional.ofNullable(Dummy.getDummyCouponType()));

        // when
        couponAdminService.createCoupon(request);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 생성 시 쿠폰의 applyItem 없을 때 테스트")
    void testCreateCoupon_NotOrderCoupon() {

        // given
        CouponCURequest request = Dummy.getDummyOrderCouponCURequest_NotOrderCoupon();
        given(couponTypeRepository.findById(request.getTypeCode())).willReturn(
            Optional.ofNullable(Dummy.getDummyCouponType()));
        given(productRepository.findById(request.getApplyItemId())).willReturn(
            Optional.ofNullable(Dummy.getDummyProduct()));


        // when
        couponAdminService.createCoupon(request);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 생성과 이미지 저장 테스트")
    void testCreateCouponSaveImage() {

        // given
        ReflectionTestUtils.setField(couponCURequest, "fileId", 1L);
        given(couponTypeRepository.findById(couponCURequest.getTypeCode())).willReturn(
            Optional.ofNullable(Dummy.getDummyCouponType()));

        given(fileRepository.findById(couponCURequest.getFileId())).willReturn(
            Optional.of(Dummy.getDummyObjectFile()));

        given(categoryRepository.findById(couponCURequest.getApplyItemId())).willReturn(
            Optional.ofNullable(Dummy.getDummyCategory()));

        // when
        couponAdminService.createCoupon(couponCURequest);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("모든 쿠폰 조회 테스트")
    void testRetrieveAllCoupons() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        given(couponRepository.findAllBy(pageRequest)).willReturn(Page.empty());

        // when
        couponAdminService.retrieveAllCoupons(pageRequest);

        // then
        BDDMockito.then(couponRepository).should().findAllBy(pageRequest);
    }

    @Test
    @DisplayName("쿠폰 단건 조회 테스트")
    void testRetrieveCoupon() {

        // given
        Long targetId = 1L;
        ReflectionTestUtils.setField(coupon, "file", Dummy.getDummyObjectFile());
        given(couponService.checkCouponExist(targetId)).willReturn(coupon);

        // when
        couponAdminService.retrieveCoupon(targetId);

        // then
        BDDMockito.then(couponService).should().checkCouponExist(targetId);
    }

    @Test
    @DisplayName("포인트 쿠폰 단건 조회 테스트")
    void testRetrievePointCoupon() {

        // given
        Long targetId = 1L;
        given(couponService.checkCouponExist(targetId)).willReturn(pointCoupon);

        // when
        couponAdminService.retrieveCoupon(targetId);

        // then
        BDDMockito.then(couponService).should().checkCouponExist(targetId);
    }

    @Test
    @DisplayName("주문 쿠폰 단건 조회 테스트")
    void testRetrieveCategoryCoupon() {

        // given
        Long targetId = 1L;
        given(couponService.checkCouponExist(targetId)).willReturn(orderCoupon);

        // when
        couponAdminService.retrieveCoupon(targetId);

        // then
        BDDMockito.then(couponService).should().checkCouponExist(targetId);
    }

    @Test
    @DisplayName("상품 쿠폰 단건 조회 테스트")
    void testRetrieveProductCoupon() {

        // given
        Long targetId = 1L;
        given(couponService.checkCouponExist(targetId)).willReturn(productCoupon);

        // when
        couponAdminService.retrieveCoupon(targetId);

        // then
        BDDMockito.then(couponService).should().checkCouponExist(targetId);
    }

    @Test
    @DisplayName("쿠폰 이미지 수정 테스트")
    void testUpdateCouponImage() {

        // given
        Long targetId = 1L;
        ReflectionTestUtils.setField(coupon, "file", Dummy.getDummyObjectFile());

        given(couponService.checkCouponExist(targetId)).willReturn(coupon);
        given(fileRepository.findById(targetId)).willReturn(Optional.of(Dummy.getDummyObjectFile()));

        // when
        couponAdminService.updateCouponImage(targetId, targetId);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 이미지 삭제 테스트")
    void testDeleteCouponImage() {

        // given
        Long targetId = 1L;
        ReflectionTestUtils.setField(coupon, "file", Dummy.getDummyObjectFile());

        given(couponService.checkCouponExist(targetId)).willReturn(coupon);

        // when
        couponAdminService.deleteCouponImage(targetId);

        // then
        BDDMockito.then(couponService).should().checkCouponExist(any());
    }

    @Test
    @DisplayName("쿠폰 수정 테스트")
    void testUpdateCoupon() {

        // given
        Long targetId = coupon.getId();

        given(couponService.checkCouponExist(targetId)).willReturn(coupon);
        given(couponTypeRepository.findById(targetId)).willReturn(Optional.ofNullable(couponType));
        given(categoryRepository.findById(category.getId())).willReturn(
            Optional.ofNullable(Dummy.getDummyCategory()));

        // when
        couponAdminService.updateCoupon(coupon.getId(), couponCURequest);

        // then
        BDDMockito.then(couponRepository).should().save(any());
    }

    @Test
    @DisplayName("쿠폰 삭제 테스트")
    void testDeleteCoupon() {

        // given
        Long targetId = 1L;

        // when
        couponAdminService.deleteCoupon(targetId);

        // then
        BDDMockito.then(couponRepository).should().deleteById(targetId);
    }

    @Test
    @DisplayName("발급된 쿠폰 조회 테스트")
    void testRetrieveIssuedCoupons() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        couponAdminService.retrieveIssuedCoupons(pageRequest);

        // then
        BDDMockito.then(orderCouponRepository).should().findAllBy();
        BDDMockito.then(productCouponRepository).should().findAllBy();
    }

    @Test
    @DisplayName("발급된 쿠폰 조회 테스트")
    void testRetrieveUsedCoupon() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        couponAdminService.retrieveUsedCoupon(pageRequest);

        // then
        BDDMockito.then(orderCouponRepository).should().getUsedOrderCoupon();
        BDDMockito.then(productCouponRepository).should().getUsedProductCoupon();
    }
}
