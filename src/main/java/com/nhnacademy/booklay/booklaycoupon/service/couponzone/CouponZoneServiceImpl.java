package com.nhnacademy.booklay.booklaycoupon.service.couponzone;

import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.request.CouponZoneCreateRequest;
import com.nhnacademy.booklay.booklaycoupon.dto.couponzone.response.CouponZoneResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Coupon;
import com.nhnacademy.booklay.booklaycoupon.entity.CouponZone;
import com.nhnacademy.booklay.booklaycoupon.entity.ObjectFile;
import com.nhnacademy.booklay.booklaycoupon.exception.NotFoundException;
import com.nhnacademy.booklay.booklaycoupon.repository.coupon.CouponRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.couponzone.CouponZoneRepository;
import com.nhnacademy.booklay.booklaycoupon.repository.objectfile.ObjectFileRepository;
import com.nhnacademy.booklay.booklaycoupon.service.coupon.GetCouponService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CouponZoneServiceImpl implements CouponZoneService{

    private final CouponRepository couponRepository;
    private final CouponZoneRepository couponZoneRepository;
    private final GetCouponService couponService;
    private final ObjectFileRepository fileRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndGradeNull(true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndGradeNull(false, pageable);
    }

    @Override
    public Page<CouponZoneResponse> retrieveAdminCouponZoneGraded(Pageable pageable) {
        return couponZoneRepository.findAllByGradeNotNull(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalseAndGradeNull(true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalseAndGradeNull(false, pageable);
    }

    @Override
    public Page<CouponZoneResponse> retrieveCouponZoneGraded(Pageable pageable) {
        return couponZoneRepository.findAllByGradeNotNullAndIsBlindIsFalse(pageable);
    }

    @Override
    public void createAtCouponZone(CouponZoneCreateRequest couponRequest) {
        Long couponId = couponRequest.getCouponId();

        Coupon coupon = couponService.checkCouponExist(couponId);
        ObjectFile file = coupon.getFile();

        CouponZone couponZone = CouponZoneCreateRequest.toEntity(couponRequest, coupon.getName(),
            coupon.getIsLimited(), coupon.getMaximumDiscountAmount(), couponId);

        if(Objects.nonNull(file)) {
            couponZone.setFile(file);
        }

        couponZoneRepository.save(couponZone);
    }

    @Override
    public void deleteAtCouponZone(Long couponZoneId) {
        if(!couponZoneRepository.existsById(couponZoneId)) {
            throw new NotFoundException("couponZone", couponZoneId);
        }

        couponZoneRepository.deleteById(couponZoneId);
    }
}
