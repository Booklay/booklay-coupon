package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import static com.nhnacademy.booklay.booklaycoupon.exception.ExceptionStrings.NOT_REGISTERED_COUPON;

import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetCouponService {

    private final CouponRepository couponRepository;

    public Coupon checkCouponExist(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException(NOT_REGISTERED_COUPON));
    }

    public String isOrderOrProduct(Coupon coupon) {
        if(Objects.nonNull(coupon.getProduct())){
            return "product";
        }

        return "order";
    }

}
