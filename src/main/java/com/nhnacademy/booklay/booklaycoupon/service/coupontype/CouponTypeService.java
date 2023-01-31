package com.nhnacademy.booklay.booklaycoupon.service.coupontype;

import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.request.CouponTypeCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupontype.response.CouponTypeRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponTypeService {

    void createCouponType(CouponTypeCURequest couponTypeRequest);
    CouponType retrieveCouponType(Long typeCode);
    Page<CouponTypeRetrieveResponse> retrieveAllCouponTypes(Pageable pageable);
    void updateCouponType(CouponTypeCURequest couponTypeRequest);
    void deleteCouponType(Long couponTypeId);

}
