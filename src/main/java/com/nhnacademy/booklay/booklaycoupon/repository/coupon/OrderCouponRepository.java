package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.OrderCouponCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderCouponRepository extends JpaRepository<OrderCoupon, Long>, OrderCouponCustomRepository {
    Page<CouponRetrieveResponseFromProduct> findAllByMember_MemberNoAndCoupon_IsDuplicatableAndCoupon_CategoryNoNotNullAndIsUsedAndExpiredAtAfter(Long memberNo, Boolean isDuplicatable, boolean isUsed, LocalDateTime expiredAt, Pageable pageable);
    Optional<OrderCoupon> findFirstByMemberIsNullAndCouponId(Long couponId);
    CouponRetrieveResponseFromProduct findByCode(String code);
    List<CouponHistoryRetrieveResponse> findAllBy();
    boolean existsByCouponIdAndMemberNoIs(Long couponId, Long memberNo);
    List<OrderCoupon> findAllByCouponIdAndMemberNoIs(Long couponId, Long memberNo);

    List<CouponRetrieveResponseFromProduct> findAllByCodeInAndMemberNoAndOrderNoIsNull(List<String> couponCodeList, Long memberNo);
    List<OrderCoupon> findByCodeIn(List<String> couponCodeList);

    List<OrderCoupon> findByOrderNo(Long orderNo);
    OrderCoupon findByMemberNoAndIdIs(Long memberNo, Long id);

    @Modifying
    @Query(nativeQuery = true, value =
            "update order_coupon set is_used = true, order_no = :orderNo where member_no = :memberNo and order_coupon_no in :usedCouponList;")
    Long updateOrderCouponUsing(Long memberNo, Long orderNo, List<Long> usedCouponList);
}
