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
     * @param couponCode 쿠폰 코드
     *
     */
    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        CouponRetrieveResponseFromProduct result;
        if (couponCode.startsWith("O")){
            result = orderCouponService.retrieveCouponByCouponCode(couponCode);
        }else if (couponCode.startsWith("P")){
            result = productCouponService.retrieveCouponByCouponCode(couponCode);
        }else {
            result = orderCouponService.retrieveCouponByCouponCode(couponCode);
            if (result == null){
                result = productCouponService.retrieveCouponByCouponCode(couponCode);
            }
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
    public void couponUsing(CouponUseRequest couponUseRequest, Long memberNo) {
        productCouponService.usingCoupon(couponUseRequest.getProductCouponList(), memberNo);
        orderCouponService.usingCoupon(couponUseRequest.getCategoryCouponList(), memberNo);
    }

    @Override
    public void couponRefund(CouponRefundRequest couponRefundRequest, Long memberNo) {
        productCouponService.refundCoupon(couponRefundRequest.getOrderProductNoList(), memberNo);
        orderCouponService.refundCoupon(couponRefundRequest.getOrderNo(), memberNo);
    }
}
