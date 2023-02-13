package com.nhnacademy.booklay.booklaycoupon.repository.coupon.querydsl;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import java.util.List;

public interface ProductCouponCustomRepository {
    List<MemberCouponRetrieveResponse> getCouponsByMember(Long memberNo);
}
