package com.nhnacademy.booklay.booklaycoupon.repository.member;

import com.nhnacademy.booklay.booklaycoupon.dto.member.response.MemberRetrieveResponse;
import com.nhnacademy.booklay.booklaycoupon.entity.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{
    Page<MemberRetrieveResponse> findAllBy(Pageable pageable);

    Optional<Member> findByMemberNo(Long memberNo);

    boolean existsByMemberId(String memberId);
}
