package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.MemberCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.PointCouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
        List<MemberCouponRetrieveResponse> couponList = new ArrayList<>();
        List<MemberCouponRetrieveResponse> orderCouponList =
            orderCouponRepository.getCouponsByMember(memberNo);
        List<MemberCouponRetrieveResponse> productCouponList =
            productCouponRepository.getCouponsByMember(memberNo);

        LocalDateTime now = LocalDateTime.now();

        checkIsUsable(orderCouponList, now);
        checkIsUsable(productCouponList, now);

        couponList.addAll(orderCouponList);
        couponList.addAll(productCouponList);

        return getPage(pageable, couponList);
    }

    private void checkIsUsable(List<MemberCouponRetrieveResponse> couponList, LocalDateTime now) {
        couponList.forEach(
            c -> {
                if (Objects.nonNull(c.getUsedItemNo())) {
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

    @Override
    @Transactional(readOnly = true)
    public Page<PointCouponRetrieveResponse> retrievePointCoupons(Long memberNo,
                                                                  Pageable pageable) {
        return couponRepository.getPointCouponByMember(memberNo, pageable);
    }

    private Page<MemberCouponRetrieveResponse> getPage(Pageable pageable,
                                                       List<MemberCouponRetrieveResponse> list) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
}
