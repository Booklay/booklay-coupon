package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderCouponServiceImpl implements OrderCouponService{
    private final OrderCouponRepository orderCouponRepository;
    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNo(Long memberNo, Boolean isDuplicable, Pageable pageable) {
        return orderCouponRepository.findAllByMember_MemberNoAndCoupon_IsDuplicatableAndCoupon_CategoryNoNotNull(memberNo, isDuplicable, pageable);
    }
    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return orderCouponRepository.findByCode(couponCode);
    }

    @Override
    public List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
        List<String> couponCodeList) {
        return orderCouponRepository.findAllByCodeIn(couponCodeList);
    }

    // 벌크연산으로 변경되면 좋겠음 하지만 최대 2회연산이라 굳이 쿼리 dsl을 사용하거나 영속성에 결함을 가지게 하거나 클리어를 할정도는 아닌것으로 보임
    @Override
    public void usingCoupon(List<CouponUsingDto> categoryCouponList) {
        AtomicReference<Long> orderNo = new AtomicReference<>();
        List<OrderCoupon> couponList = orderCouponRepository.findByCodeIn(categoryCouponList.stream()
            .map(couponUsingDto -> {
                orderNo.set(couponUsingDto.getUsedTargetNo());
                return couponUsingDto.getCouponCode();
            }).collect(Collectors.toList()));

        couponList.forEach(orderCoupon -> orderCoupon.setOrderNo(orderNo.get()));
        orderCouponRepository.flush();
    }

    @Override
    public void refundCoupon(Long orderNo) {
        List<OrderCoupon> couponList = orderCouponRepository.findByOrderNo(orderNo);
        couponList.forEach(orderCoupon -> orderCoupon.setOrderNo(null));
        orderCouponRepository.flush();
    }

}
