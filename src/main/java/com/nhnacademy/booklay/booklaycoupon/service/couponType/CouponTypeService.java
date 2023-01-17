package com.nhnacademy.booklay.booklaycoupon.service.couponType;

import com.nhnacademy.booklay.booklaycoupon.dto.couponType.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponType.response.CouponTypeRetrieveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponTypeService {

    void createCouponType(CouponTypeCURequest couponTypeRequest);
    Page<CouponTypeRetrieveResponse> retrieveAllCouponTypes(Pageable pageable);
    void updateCouponType(Long couponTypeId, CouponTypeCURequest couponTypeRequest);
    void deleteCouponType(Long couponTypeId);
}
