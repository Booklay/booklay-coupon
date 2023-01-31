package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.repository.CategoryRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.ImageRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTypeRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponAdminServiceImpl;
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

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CouponAdminServiceImplTest {

    @InjectMocks
    private CouponAdminServiceImpl couponAdminService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    Coupon coupon;
    List<Coupon> couponList;
    CouponType couponType;
    CouponCURequest couponCURequest;
    Category category;

    @BeforeEach
    void setUp() {
        coupon = Dummy.getDummyCoupon();
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
        given(couponRepository.findById(targetId)).willReturn(Optional.ofNullable(coupon));

        // when
        couponAdminService.retrieveCoupon(targetId);

        // then
        BDDMockito.then(couponRepository).should().findById(targetId);
    }

    @Test
    @DisplayName("쿠폰 수정 테스트")
    void testUpdateCoupon() {

        // given
        Long targetId = coupon.getId();

        given(couponRepository.findById(targetId)).willReturn(Optional.ofNullable(coupon));
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
        given(couponRepository.existsById(targetId)).willReturn(true);

        // when
        couponAdminService.deleteCoupon(targetId);

        // then
        BDDMockito.then(couponRepository).should().deleteById(targetId);
    }
}
