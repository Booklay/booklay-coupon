package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberOrderCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponMemberServiceImpl implements CouponMemberService {

    private final CouponRepository couponRepository;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<MemberCouponRetrieveResponse> retrieveCoupons(Long memberNo, Pageable pageable) {
        List<MemberCouponRetrieveResponse> couponList =
            retrieveCouponList(memberNo);

        return getPage(pageable, couponList);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberNo,
                                                                  Pageable pageable) {
        return couponRepository.getPointCouponByMember(memberNo, pageable);
    }

    /**
     * 사용자가 소유한 쿠폰의 갯수를 조회합니다.
     */
    @Override
    public int retrieveCouponCount(Long memberNo) {
        List<MemberCouponRetrieveResponse> couponList = retrieveCouponList(memberNo);
        List<MemberCouponRetrieveResponse> usuableList =
            couponList.stream().filter(c -> !c.getIsUsed()).collect(Collectors.toList());

        return usuableList.size();
    }

    /**
     * 사용자의 포인트 쿠폰 사용
     */
    @Override
    public void usePointCoupon(Long memberNo, Long orderCouponId) {
        OrderCoupon orderCoupon =
            orderCouponRepository.findByMemberNoAndIdIs(memberNo, orderCouponId);
        orderCoupon.setIsUsed(true);

        orderCouponRepository.save(orderCoupon);
    }

    public List<MemberCouponRetrieveResponse> retrieveCouponList(Long memberNo) {
        List<MemberCouponRetrieveResponse> couponList = new ArrayList<>();

        List<MemberOrderCouponRetrieveResponse> orderList =
            orderCouponRepository.getCouponsByMember(memberNo);
        List<MemberCouponRetrieveResponse> productCouponList =
            productCouponRepository.getCouponsByMember(memberNo);

        List<MemberCouponRetrieveResponse> orderCouponList =
            orderList.stream().map(MemberCouponRetrieveResponse::fromOrderCoupon)
                .collect(
                    Collectors.toList());
        LocalDateTime now = LocalDateTime.now();

        checkIsUsable(orderCouponList, now);
        checkIsUsable(productCouponList, now);

        couponList.addAll(orderCouponList);
        couponList.addAll(productCouponList);

        return couponList;
    }

    private void checkIsUsable(List<MemberCouponRetrieveResponse> couponList, LocalDateTime now) {
        couponList.forEach(
            c -> {
                if (Objects.nonNull(c.getItemId())) {
                    c.setIsUsed(true);
                    c.setReason("사용 완료");
                }

                if (Objects.nonNull(c.getExpiredAt()) && c.getExpiredAt().isBefore(now)) {
                    c.setIsUsed(true);
                    c.setReason("기간 만료");
                }
            }
        );
    }

    private Page<MemberCouponRetrieveResponse> getPage(Pageable pageable,
                                                       List<MemberCouponRetrieveResponse> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
