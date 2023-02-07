package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.nhnacademy.booklay.booklaycoupon.dummy.Dummy;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan("com.nhnacademy.booklay.booklaycoupon.config")
class OrderCouponRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderCouponRepository orderCouponRepository;

    @Autowired
    private CouponRepository couponRepository;

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
}
