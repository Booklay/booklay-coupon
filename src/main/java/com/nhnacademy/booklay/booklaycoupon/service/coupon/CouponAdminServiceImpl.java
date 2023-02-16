package com.nhnacademy.booklay.booklaycoupon.service.coupon;


import com.nhnacademy.booklay.booklaycoupon.dto.coupon.request.CouponCURequest;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponDetailRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponHistoryRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.dto.coupon.response.CouponUsedHistoryResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Category;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponType;
import com.nhnacademy.booklay.booklaycoupon.entity.Image;
import com.nhnacademy.booklay.booklaycoupon.entity.ObjectFile;
import com.nhnacademy.booklay.booklaycoupon.entity.OrderCoupon;
import com.nhnacademy.booklay.booklaycoupon.entity.Product;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.CategoryRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.ProductRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponTypeRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.OrderCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.ProductCouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.objectfile.ObjectFileRepository;
import com.nhnacademy.booklay.booklaycoupon.service.RestService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final ObjectFileRepository fileRepository;
    private final GetCouponService couponService;
    private final OrderCouponRepository orderCouponRepository;
    private final ProductCouponRepository productCouponRepository;

    private final RestService restService;
    private static final int PAGE_SIZE = 20;

    /**
     * 쿠폰을 생성합니다.
     * @param couponRequest 쿠폰 생성에 필요한 요청 객체.
     */
    @Override
    public Coupon createCoupon(CouponCURequest couponRequest) {
        Long typeCode = couponRequest.getTypeCode();
        Long fileId = couponRequest.getFileId();

        CouponType couponType = couponTypeRepository.findById(typeCode)
            .orElseThrow(() -> new NotFoundException(CouponType.class.toString(), typeCode));

        Coupon coupon = CouponCURequest.toEntity(couponRequest, couponType);

        if(Objects.nonNull(couponRequest.getFileId())) {
            ObjectFile file = fileRepository.findById(fileId)
                .orElseThrow(() -> new NotFoundException(Image.class.toString(), fileId));

            coupon.setFile(file);
        }

        setCategoryOrProduct(coupon, couponRequest);

        return couponRepository.save(coupon);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponRetrieveResponse> retrieveAllCoupons(Pageable pageable) {
        return couponRepository.findAllBy(pageable);
    }

    /**
     * 쿠폰 상세 조회
     * @param couponId 상세 조회하려는 쿠폰의 id
     */
    @Override
    @Transactional(readOnly = true)
    public CouponDetailRetrieveResponse retrieveCoupon(Long couponId) {
        Coupon coupon = couponService.checkCouponExist(couponId);

        CouponDetailRetrieveResponse response =
            CouponDetailRetrieveResponse.fromEntity(coupon);

        if(coupon.getCouponType().getName().equals("포인트")) {
            response.setIsOrderCoupon(true);
        }

        if(Objects.nonNull(coupon.getCategory())) {
            response.setApplyItemId(coupon.getCategory().getId());
            response.setItemName(coupon.getCategory().getName());
            response.setIsOrderCoupon(true);
        }

        if(Objects.nonNull(coupon.getProduct())) {
            response.setApplyItemId(coupon.getProduct().getId());
            response.setItemName(coupon.getProduct().getTitle());
            response.setIsOrderCoupon(false);
        }

        if(Objects.nonNull(coupon.getFile())) {
            response.setObjectFileId(coupon.getFile().getId());
        }

        return response;
    }

    /**
     * 쿠폰 정보 수정
     * @param couponId 수정하려는 쿠폰의 id
     * @param couponRequest 쿠폰 수정 객체
     */
    @Override
    public void updateCoupon(Long couponId, CouponCURequest couponRequest) {
        Coupon coupon = couponService.checkCouponExist(couponId);

        CouponType couponType = couponTypeRepository.findById(couponRequest.getTypeCode())
            .orElseThrow(() -> new NotFoundException(CouponType.class.toString(), couponRequest.getTypeCode()));

        coupon.update(couponRequest, couponType);
        setCategoryOrProduct(coupon, couponRequest);

        couponRepository.save(coupon);
    }

    /**
     * 등록된 쿠폰의 이미지를 수정합니다.
     * @param objectFileId 오브젝트 스토리지 상의 Id
     */
    @Override
    public void updateCouponImage(Long couponId, Long objectFileId) {
        Coupon coupon = couponService.checkCouponExist(couponId);

        ObjectFile file = fileRepository.findById(objectFileId)
            .orElseThrow(() -> new NotFoundException(Image.class.toString(), objectFileId));

        coupon.setFile(file);

        couponRepository.save(coupon);
    }

    /**
     * 쿠폰의 이미지를 삭제합니다.
     */
    @Override
    public void deleteCouponImage(Long couponId) {
        Coupon coupon = couponService.checkCouponExist(couponId);
        coupon.setFile(null);
    }

    /**
     * 쿠폰을 삭제합니다.
     */
    @Override
    public void deleteCoupon(Long couponId) {
        if(!couponRepository.existsById(couponId)) {
            throw new NotFoundException(Coupon.class.toString(), couponId);
        }
        couponRepository.deleteById(couponId);
    }

    /**
     * 발급된 쿠폰을 조회하기 위해, 상품 쿠폰과 주문 쿠폰을 조회하고 Page로 반환합니다.
     *
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CouponHistoryRetrieveResponse> retrieveIssuedCoupons(Pageable pageable) {
        List<CouponHistoryRetrieveResponse> couponHistoryList = new ArrayList<>();

        List<CouponHistoryRetrieveResponse> atOrder = orderCouponRepository.findAllBy();
        List<CouponHistoryRetrieveResponse> atProduct = productCouponRepository.findAllBy();

        couponHistoryList.addAll(atOrder);
        couponHistoryList.addAll(atProduct);

        couponHistoryList.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        return getHistoryPage(pageable, couponHistoryList);
    }

    @Override
    public Page<CouponUsedHistoryResponse> retrieveUsedCoupon(Pageable pageable) {
        return null;
    }

    /**
     * List를 Page로 바꿈.
     *
     */
    private static Page<CouponHistoryRetrieveResponse> getHistoryPage(Pageable pageable,
                                                                      List<CouponHistoryRetrieveResponse> couponHistoryList) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), couponHistoryList.size());

        return new PageImpl<>(couponHistoryList.subList(start, end), pageable, couponHistoryList.size());
    }

    private void setCategoryOrProduct(Coupon coupon, CouponCURequest couponRequest) {

        boolean isOrderCoupon = couponRequest.getIsOrderCoupon();
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
