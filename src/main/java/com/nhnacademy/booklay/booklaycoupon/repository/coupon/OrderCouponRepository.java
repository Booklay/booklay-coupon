package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Long> {
    Page<CouponRetrieveResponseFromProduct> findAllByMember_MemberNoAndCoupon_IsDuplicatable(Long memberNo, Boolean isDuplicatable, Pageable pageable);
    Optional<OrderCoupon> findByMemberIsNullAndCouponId(Long couponId);
    CouponRetrieveResponseFromProduct findByCode(String code);
    List<CouponHistoryRetrieveResponse> findAllBy();
}
