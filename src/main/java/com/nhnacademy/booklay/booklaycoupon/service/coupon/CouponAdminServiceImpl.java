package com.nhnacademy.booklay.booklaycoupon.service.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.Image;
import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.CategoryRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTypeRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.ImageRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.ProductRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 김승혜
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponAdminServiceImpl implements CouponAdminService{

    private final CouponRepository couponRepository;
    private final CouponTypeRepository couponTypeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    @Override
    public Coupon createCoupon(CouponCURequest couponRequest) {
        Long typeCode = couponRequest.getTypeCode();
        Long imageId = couponRequest.getImageId();

        CouponType couponType = couponTypeRepository.findById(typeCode)
            .orElseThrow(() -> new NotFoundException(CouponType.class.toString(), typeCode));

        Image image = imageRepository.findById(imageId)
            .orElseThrow(() -> new NotFoundException(Image.class.toString(), imageId));

        Coupon coupon = CouponCURequest.toEntity(couponRequest, couponType, image);
        setCategoryOrProduct(coupon, couponRequest);

        return couponRepository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponRetrieveResponse> retrieveAllCoupons(Pageable pageable) {
        return couponRepository.findAllBy(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public CouponDetailRetrieveResponse retrieveCoupon(Long couponId) {
        return CouponDetailRetrieveResponse.fromEntity(couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("No Such Coupon.")));
    }

    @Override
    public void updateCoupon(Long couponId, CouponCURequest couponRequest) {
        Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException(Coupon.class.toString(), couponId));

        CouponType couponType = couponTypeRepository.findById(couponRequest.getTypeCode())
            .orElseThrow(() -> new NotFoundException(CouponType.class.toString(), couponRequest.getTypeCode()));

        coupon.update(couponRequest, couponType);
        setCategoryOrProduct(coupon, couponRequest);

        couponRepository.save(coupon);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        if(!couponRepository.existsById(couponId)) {
            throw new NotFoundException(Coupon.class.toString(), couponId);
        }
        couponRepository.deleteById(couponId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CouponHistoryRetrieveResponse> retrieveIssuedCoupons() {
        List<CouponHistoryRetrieveResponse> couponHistoryAtOrderCoupon =
            couponRepository.getCouponHistoryAtOrderCoupon();
        List<CouponHistoryRetrieveResponse> couponHistoryAtProductCoupon = couponRepository.getCouponHistoryAtProductCoupon();


        List<CouponHistoryRetrieveResponse> couponHistoryList = new ArrayList<>();
        couponHistoryList.addAll(couponHistoryAtOrderCoupon);
        couponHistoryList.addAll(couponHistoryAtProductCoupon);

        couponHistoryList.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        return couponHistoryList;
    }

    private void setCategoryOrProduct(Coupon coupon, CouponCURequest couponRequest) {

        Boolean isOrderCoupon = couponRequest.getIsOrderCoupon();
        Long applyItemId = couponRequest.getApplyItemId();

        if(Objects.isNull(applyItemId)) {
            coupon.setCategory(null);
            coupon.setProduct(null);
        } else if (isOrderCoupon) {
            // 주문 쿠폰일 때,
            Category category = categoryRepository.findById(applyItemId)
                .orElseThrow(() -> new NotFoundException(Category.class.toString(), applyItemId));

            coupon.setCategory(category);
        } else {
            // 상품 쿠폰일 때,
            Product product = productRepository.findById(applyItemId)
                .orElseThrow(() -> new NotFoundException(Product.class.toString(), applyItemId));

            coupon.setProduct(product);
        }
    }
}
