package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.OrderCouponCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Long>, OrderCouponCustomRepository {
    Page<CouponRetrieveResponseFromProduct> findAllByMember_MemberNoAndCoupon_IsDuplicatableAndCoupon_CategoryNoNotNull(Long memberNo, Boolean isDuplicatable, Pageable pageable);
    Optional<OrderCoupon> findFirstByMemberIsNullAndCouponId(Long couponId);
    CouponRetrieveResponseFromProduct findByCode(String code);
    List<CouponHistoryRetrieveResponse> findAllBy();
    boolean existsByCouponIdAndMemberNoIs(Long couponId, Long memberNo);

    List<CouponRetrieveResponseFromProduct> findAllByCodeIn(List<String> couponCodeList);
    List<OrderCoupon> findByCodeIn(List<String> couponCodeList);

    List<OrderCoupon> findByOrderNo(Long orderNo);
}
