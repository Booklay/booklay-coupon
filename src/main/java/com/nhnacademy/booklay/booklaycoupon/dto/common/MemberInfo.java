package com.nhnacademy.booklay.booklaycoupon.dto.common;

import com.nhnacademy.booklay.booklaycoupon.dto.member.response.MemberRetrieveResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Getter
@NoArgsConstructor
public class MemberInfo {

    private Long memberNo;
    private String gender;
    private String memberId;
    private String nickname;
    private String name;
    private LocalDate birthday;
    private String phoneNo;
    private String email;

    public MemberInfo (MemberRetrieveResponse memberRetrieveResponse) {
        this.memberNo = memberRetrieveResponse.getMemberNo();
        this.gender = memberRetrieveResponse.getGender();
        this.memberId = memberRetrieveResponse.getMemberId();
        this.nickname = memberRetrieveResponse.getNickname();
        this.name = memberRetrieveResponse.getName();
        this.birthday = memberRetrieveResponse.getBirthday();
        this.phoneNo = memberRetrieveResponse.getPhoneNo();
        this.email = memberRetrieveResponse.getEmail();
    }

}
