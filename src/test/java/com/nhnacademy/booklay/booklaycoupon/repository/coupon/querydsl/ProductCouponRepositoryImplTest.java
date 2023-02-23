package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.util.CodeUtils;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@ActiveProfiles("test")
class ProductCouponRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductCouponRepositoryImpl productCouponRepository;

    @Test
    void getCouponsByMember() {

        // given
        ProductCoupon productCoupon = new ProductCoupon(Dummy.getDummyCoupon_Product(), CodeUtils.getProductCouponCode());
        ReflectionTestUtils.setField(productCoupon, "member", Dummy.getDummyMember());

        // when
        List<MemberCouponRetrieveResponse> list =
            productCouponRepository.getCouponsByMember(1L);

        // then
        assertThat(list).isNotNull();
    }

    @Test
    void getUsedProductCoupon() {

        // given
        ProductCoupon productCoupon = new ProductCoupon(Dummy.getDummyCoupon_Product(), CodeUtils.getProductCouponCode());
        ReflectionTestUtils.setField(productCoupon, "member", Dummy.getDummyMember());

        // when
        List<CouponUsedHistoryResponse> list =
            productCouponRepository.getUsedProductCoupon();

        // then
        assertThat(list).isNotNull();
    }
}