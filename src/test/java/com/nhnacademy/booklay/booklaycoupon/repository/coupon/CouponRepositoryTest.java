package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.CouponRepositoryImpl;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
@ActiveProfiles("test")
class CouponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponRepositoryImpl couponRepositoryImpl;

    @Autowired
    private OrderCouponRepository orderCouponRepository;

    Coupon coupon;

    void clearRepo(String entityName, JpaRepository jpaRepository) {
        jpaRepository.deleteAll();

        String query = String.format("ALTER TABLE `%s` ALTER COLUMN `%s_no` RESTART WITH 1", entityName, entityName);

        this.entityManager
            .getEntityManager()
            .createNativeQuery(query)
            .executeUpdate();
    }

    @BeforeEach
    void setUp() {
        clearRepo("coupon", couponRepository);
        coupon = Dummy.getDummyCoupon();

        entityManager.persist(coupon.getCouponType());

        couponRepository.save(coupon);
    }

    @Test
    @DisplayName("CouponRepository save test")
    void testCouponSave() {

        //given

        //when
        Coupon expected = couponRepository.save(coupon);

        //then
        assertThat(expected.getName()).isEqualTo(coupon.getName());
    }

    @Test
    @DisplayName("findById() - ?????? ?????? ?????? ?????????")
    void testCouponFindById() {

        //given

        //when
        Coupon expected = couponRepository.findById(coupon.getId()).orElseThrow(() -> new IllegalArgumentException("Coupon not found."));

        //then
        assertThat(expected.getId()).isEqualTo(coupon.getId());
    }

    @Test
    @DisplayName("getCoupons() - ?????? ????????? ?????? ?????????")
    void testGetCoupons() {

        //given

        //when
        List<Coupon> dtoList = couponRepository.findAll();

        //then
        assertThat(dtoList.size()).isEqualTo(1);
        assertThat(dtoList.get(0).getName()).isEqualTo(coupon.getName());
    }

    @Test
    @Order(1)
    @DisplayName("deleteCoupon() - ?????? ?????? ?????????")
    void testDeleteCoupon() {

        //given

        //when
        System.out.println(couponRepository.findAll());
        couponRepository.deleteById(coupon.getId());

        //then
        assertThat(couponRepository.findAll().size()).isZero();
    }

    @Test
    @DisplayName("findAllBy() - Page<Coupon> ?????? ?????????")
    void testFindAllBy() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 10);

        // when
        Page<CouponRetrieveResponse> couponPage = couponRepository.findAllBy(pageRequest);

        // then
        assertThat(couponPage).isNotNull();
    }

    @Test
    @DisplayName("getPointCouponByMember - ???????????? ????????? ?????? ?????? ?????? (queryDsl).")
    void testGetPointCouponByMember() {

        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        Coupon dummyCouponPoint = Dummy.getDummyCoupon_Point();

        OrderCoupon orderCoupon = new OrderCoupon(dummyCouponPoint, "code", false);
        orderCoupon.setExpiredAt(LocalDateTime.now().plusDays(1));
        ReflectionTestUtils.setField(orderCoupon, "member", Dummy.getDummyMember());
        orderCouponRepository.save(orderCoupon);

        // when
        Page<PointCouponRetrieveResponse> couponPage = couponRepositoryImpl.getPointCouponByMember(1L, pageRequest);

        // then
        assertThat(couponPage).isNotNull();
    }

}
