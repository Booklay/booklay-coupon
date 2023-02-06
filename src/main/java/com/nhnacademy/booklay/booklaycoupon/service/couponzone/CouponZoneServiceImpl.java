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
    private final ObjectFileRepository fileRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIs(true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveAdminUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIs(false, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneLimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalse(true, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CouponZoneResponse> retrieveCouponZoneUnlimited(Pageable pageable) {
        return couponZoneRepository.findAllByIsLimitedIsAndIsBlindIsFalse(false, pageable);
    }

    @Override
    public void createAtCouponZone(CouponZoneCreateRequest couponRequest) {
        Long couponId = couponRequest.getCouponId();

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException("Coupon", couponId));
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
