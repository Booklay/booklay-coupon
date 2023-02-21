package com.nhnacademy.booklay.booklaycoupon.repository.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl.ProductCouponCustomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long>,
    ProductCouponCustomRepository {
    Page<CouponRetrieveResponseFromProduct> findAllByCoupon_ProductNoAndMember_MemberNoAndCoupon_IsDuplicatableAndOrderProductNoIsNullAndExpiredAtAfter(Long productNo, Long memberNo, Boolean isDuplicatable, LocalDateTime expiredAt, Pageable pageable);
    CouponRetrieveResponseFromProduct findByCode(String code);
    Optional<ProductCoupon> findFirstByMemberIsNullAndCouponId(Long couponId);
    List<CouponHistoryRetrieveResponse> findAllBy();
    boolean existsByCouponIdAndMemberNoIs(Long couponId, Long memberNo);
    List<ProductCoupon> findAllByCouponIdAndMemberNoIs(Long couponId, Long memeberNo);
    List<CouponRetrieveResponseFromProduct> findAllByCodeInAndMemberNoAndOrderProductNoIsNull(List<String> couponCodeList, Long memberNo);

    List<ProductCoupon> findAllByOrderProductNoIn(List<Long> orderProductNoList);
}
