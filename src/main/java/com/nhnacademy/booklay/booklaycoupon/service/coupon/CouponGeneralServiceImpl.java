package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponGeneralServiceImpl implements CouponGeneralService{
    private final ProductCouponService productCouponService;

    /**
     * 코드 해독기능이 달려 각각의 쿠폰에 맞는서비스에서 쿠폰을 조회하여 반환
     * //todo DTO가 쿠폰 3종류 모두를 지원하는 형태가 필요함 - 미확인
     * @param couponCode 쿠폰 코드
     *
     */
    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return productCouponService.retrieveCouponByCouponCode(couponCode);
    }
}
