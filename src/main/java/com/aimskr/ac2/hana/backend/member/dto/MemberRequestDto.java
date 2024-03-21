package com.aimskr.ac2.hana.backend.member.dto;

import com.aimskr.ac2.hana.backend.member.domain.Member;
import com.aimskr.ac2.hana.backend.member.domain.MemberRole;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {
    private String account;
    private String name;
    private LocalDate birth;
    private String phone;
    private String password;
    private MemberRole role;
    private Boolean isQaAssign;
    private String email;

    // Entity to DTO
    public static MemberRequestDto of(Member member) {
        return MemberRequestDto.builder()
                .account(member.getAccount())
                .name(member.getName())
                .birth(member.getBirth())
                .phone(member.getPhone())
                .password(member.getPassword())
                .role(member.getRole())
                .isQaAssign(member.getIsQaAssign())
                .email(member.getEmail())
                .build();
    }

    // DTO to Entity
    public Member toEntity() {
        // 미입력값 기본 설정
        if (this.birth == null) this.birth= LocalDate.now();
        if (this.isQaAssign == null) this.isQaAssign = false;

        return Member.builder()
                .account(account)
                .name(name)
                .birth(birth)
                .phone(phone)
                .password(password)
                .role(role)
                .isQaAssign(isQaAssign)
                .email(email)
                .build();
    }
}
