package com.nhnacademy.booklay.booklaycoupon.controller.coupon;

import com.nhnacademy.booklay.booklaycoupon.service.coupon.CouponWelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CouponWelcomeController {

    private final CouponWelcomeService welcomeService;

    @PostMapping("/welcome/{memberNo}")
    public ResponseEntity<Void> issueWelcomeCouponToMember(@PathVariable Long memberNo) {
        welcomeService.issueWelcomeCoupon(memberNo);

        return ResponseEntity.status(HttpStatus.OK)
            .build();
    }

}
