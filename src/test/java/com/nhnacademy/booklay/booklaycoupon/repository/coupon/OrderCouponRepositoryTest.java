package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberOrderCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.OrderCouponRepositoryImpl;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class OrderCouponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderCouponRepository orderCouponRepository;

    @Autowired
    private OrderCouponRepositoryImpl orderCouponRepositoryImpl;

    private Coupon coupon;
    private OrderCoupon orderCoupon;

    @BeforeEach
    void setUp() {
        coupon = Coupon.builder()
            .couponType(Dummy.getDummyCouponType())
            .name("이달의 쿠폰")
            .amount(5)
            .minimumUseAmount(1000)
            .maximumDiscountAmount(3000)
            .isDuplicatable(false)
            .build();

        coupon.setIsLimited(true);

        orderCoupon = OrderCoupon.builder()
            .coupon(coupon)
            .code(UUID.randomUUID().toString().substring(0, 30))
            .isUsed(false)
            .build();
    }

    @Test
    @DisplayName("주문쿠폰 save 테스트")
    void testOrderCouponSave() {

        // given
        entityManager.persist(coupon.getCouponType());
        entityManager.persist(coupon);

        // when
        OrderCoupon expected = orderCouponRepository.save(orderCoupon);

        // then
        assertThat(expected).isNotNull();

    }

    @Test
    @DisplayName("주문쿠폰 findById() 테스트")
    void testOrderCouponFindById() {

        // given
        entityManager.persist(coupon.getCouponType());
        entityManager.persist(coupon);
        orderCouponRepository.save(orderCoupon);

        // when
        OrderCoupon expected = orderCouponRepository.findById(orderCoupon.getId())
            .orElseThrow(() -> new NotFoundException(OrderCoupon.class.toString(),
                orderCoupon.getId()));

        // then
        assertThat(expected.getId()).isEqualTo(orderCoupon.getId());
    }

    @Test
    @DisplayName("사용자가 소유한 쿠폰 조회 테스트")
    void testGetCouponsByMember() {

        // given

        // when
        List<CouponUsedHistoryResponse> list =
            orderCouponRepositoryImpl.getUsedOrderCoupon();

        // then
        assertThat(list).isNotNull();
    }

    @Test
    @DisplayName("사용자가 사용한 주푼 쿠폰 조회 테스트")
    void testGetUsedOrderCoupon() {

        // given

        // when
        List<MemberOrderCouponRetrieveResponse> list =
            orderCouponRepositoryImpl.getCouponsByMember(1L);

        // then
        assertThat(list).isNotNull();
    }
}
