package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.member.MemberRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
class CouponRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CouponRepository couponRepository;

    @Autowired
    OrderCouponRepository orderCouponRepository;

    @Autowired
    ProductCouponRepository productCouponRepository;

    @Autowired
    MemberRepository memberRepository;

    Coupon coupon;
    OrderCoupon orderCoupon;
    ProductCoupon productCoupon;
    Member member;

    @BeforeEach
    void setUp() {
        clearRepo("coupon", couponRepository);
        clearRepo("member", memberRepository);
        clearRepo("order_coupon", orderCouponRepository);
        coupon = Dummy.getDummyCoupon();
        orderCoupon = Dummy.getDummyOrderCoupon();
        productCoupon = Dummy.getDummyProductCoupon();
        member = Dummy.getDummyMember();
        ReflectionTestUtils.setField(orderCoupon, "member", member);
        ReflectionTestUtils.setField(productCoupon, "member", member);
    }

    @Test
    void getCouponHistoryAtOrderCoupon() {
        // given
        entityManager.persist(coupon.getCouponType());
        couponRepository.save(coupon);
        entityManager.persist(member.getGender());
        memberRepository.save(member);
        orderCouponRepository.save(orderCoupon);

        // when
        List<CouponHistoryRetrieveResponse> result =
            couponRepository.getCouponHistoryAtOrderCoupon();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getCouponHistoryAtProductCoupon() {
        // given
        entityManager.persist(coupon.getCouponType());
        couponRepository.save(coupon);
        entityManager.persist(member.getGender());
        memberRepository.save(member);
        productCouponRepository.save(productCoupon);

        // when
        List<CouponHistoryRetrieveResponse> result =
            couponRepository.getCouponHistoryAtProductCoupon();

        // then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getPointCouponByMember() {
        // given
        PageRequest pageRequest = PageRequest.of(0, 10);
        entityManager.persist(coupon.getCouponType());
        couponRepository.save(coupon);
        entityManager.persist(member.getGender());
        memberRepository.save(member);
        orderCouponRepository.save(orderCoupon);

        // when
        Page<PointCouponRetrieveResponse> result =
            couponRepository.getPointCouponByMember(1L, pageRequest);

        // then
        assertThat(result.getContent().size()).isZero();
    }

    void clearRepo(String entityName, JpaRepository jpaRepository) {
        jpaRepository.deleteAll();

        String query = String.format("ALTER TABLE `%s` ALTER COLUMN `%s_no` RESTART WITH 1", entityName, entityName);

        this.entityManager
            .getEntityManager()
            .createNativeQuery(query)
            .executeUpdate();
    }
}
