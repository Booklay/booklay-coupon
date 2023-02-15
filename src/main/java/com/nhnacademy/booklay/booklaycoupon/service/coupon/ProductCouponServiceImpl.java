package com.nhnacademy.booklay.booklaycoupon.service.coupon;

import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponUsingDto;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponseFromProduct;
import com.nhnacademy.booklay.booklaycoupon.entity.ProductCoupon;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCouponServiceImpl implements ProductCouponService{
    private final ProductCouponRepository productCouponRepository;

    @Override
    public Page<CouponRetrieveResponseFromProduct> retrieveCouponPageByMemberNoAndProductNo(Long memberNo, Long productNo, Boolean isDuplicatable, Pageable pageable){
        return productCouponRepository.findAllByCoupon_Product_IdAndMember_MemberNoAndCoupon_IsDuplicatable(productNo, memberNo, isDuplicatable, pageable);
    }

    @Override
    public CouponRetrieveResponseFromProduct retrieveCouponByCouponCode(String couponCode) {
        return productCouponRepository.findByCode(couponCode);
    }

    @Override
    public List<CouponRetrieveResponseFromProduct> retrieveCouponByCouponCodeList(
            List<String> couponCodeList, Long memberNo) {
        return productCouponRepository.findAllByCodeInAndMemberNoAndOrderProductNoNotNull(couponCodeList, memberNo);
    }

    
    //todo 벌크연산으로 변경필요
    @Override
    public void usingCoupon(List<CouponUsingDto> productCouponList) {
        Map<Long, CouponUsingDto> usingDtoMap = new HashMap<>();
        List<ProductCoupon> couponList = productCouponRepository.findAllById(productCouponList.stream()
            .map(couponUsingDto -> {
                usingDtoMap.put(couponUsingDto.getUsedTargetNo(), couponUsingDto);
                return couponUsingDto.getSpecifiedCouponNo();
            }).collect(Collectors.toList()));

        couponList.forEach(productCoupon -> productCoupon.setOrderProductNo(usingDtoMap.get(productCoupon.getId()).getUsedTargetNo()));
        productCouponRepository.flush();
    }

    //todo 벌크연산으로 변경 필요
    @Override
    public void refundCoupon(List<Long> orderProductNoList) {
        List<ProductCoupon> couponList = productCouponRepository.findAllByOrderProductNoIn(orderProductNoList);
        couponList.forEach(productCoupon -> productCoupon.setOrderProductNo(null));
        productCouponRepository.flush();
    }

}
