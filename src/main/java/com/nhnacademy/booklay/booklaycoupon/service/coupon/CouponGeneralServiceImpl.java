package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponRefundRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUseRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponGeneralServiceImpl implements CouponGeneralService{
    private final ProductCouponService productCouponService;
    private final OrderCouponService orderCouponService;
    /**
     * 코드 해독기능이 달려 각각의 쿠폰에 맞는서비스에서 쿠폰을 조회하여 반환
     * //todo DTO가 쿠폰 3종류 모두를 지원하는 형태가 필요함 - 미확인
     * @param couponCode 쿠폰 코드
     *
     */
    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        CouponRetrieveResponseFromProduct result = productCouponService.retrieveCouponByCouponCode(couponCode);
        if (result == null){
            result = orderCouponService.retrieveCouponByCouponCode(couponCode);
        }
        return result;
    }

    @Override
    public List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
            List<String> couponCodeList, Long memberNo) {
        List<CouponRetrieveResponseFromProduct> result = productCouponService.retrieveCouponByCouponCodeList(couponCodeList, memberNo);

        result.addAll(orderCouponService.retrieveCouponByCouponCodeList(couponCodeList, memberNo));

        return result;
    }

    @Override
    public void couponUsing(CouponUseRequest couponUseRequest) {
        productCouponService.usingCoupon(couponUseRequest.getProductCouponList());
        orderCouponService.usingCoupon(couponUseRequest.getCategoryCouponList());
    }

    @Override
    public void couponRefund(CouponRefundRequest couponRefundRequest) {
        productCouponService.refundCoupon(couponRefundRequest.getOrderProductNoList());
        orderCouponService.refundCoupon(couponRefundRequest.getOrderNo());
    }
}
