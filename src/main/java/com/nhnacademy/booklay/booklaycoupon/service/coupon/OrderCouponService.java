package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderCouponService {
    Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNo(Long memberNo, Boolean isDuplicable, Pageable pageable);
}
