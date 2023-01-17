package com.nhnacademy.booklay.booklaycoupon.repository.member;

import com.nhnacademy.booklay.booklaycoupon.dto.member.response.MemberLoginResponse;
import java.util.Optional;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MemberRepositoryCustom {

    Optional<MemberLoginResponse> retrieveMemberByUserId(String userId);
}
