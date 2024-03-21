package com.aimskr.ac2.hana.backend.member.dto;

import com.aimskr.ac2.hana.backend.member.domain.Member;
import com.aimskr.ac2.hana.backend.member.domain.MemberRole;
import lombok.*;

import java.time.LocalDate;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private Long id;
    private String account;
    private String name;
    private LocalDate birth;
    private String phone;
    private String password;
    private MemberRole role;
    private Boolean isQaAssign;
    private Boolean isLock; // 비밀번호 잠금 여부
    private Boolean shouldModi;  // 비밀번호 재설정 필요 여부
    private Integer pwdRemainingDay; // 패스워드 변경 잔여일
    private String email;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.account = member.getAccount();
        this.name = member.getName();
        this.birth = member.getBirth();
        this.phone = member.getPhone();
        this.password = member.getPassword();
        this.role = member.getRole();
        this.isQaAssign = member.getIsQaAssign();
        this.isLock = member.getIsLock();
        this.shouldModi = member.getShouldModi();
        this.pwdRemainingDay = member.getPwdRemainingDay();
        this.email = member.getEmail();
    }

    // Entity to DTO
    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .account(member.getAccount())
                .name(member.getName())
                .birth(member.getBirth())
                .phone(member.getPhone())
                .password(member.getPassword())
                .role(member.getRole())
                .isQaAssign(member.getIsQaAssign())
                .isLock(member.getIsLock())
                .shouldModi(member.getShouldModi())
                .pwdRemainingDay(member.getPwdRemainingDay())
                .email(member.getEmail())
                .build();
    }
}
