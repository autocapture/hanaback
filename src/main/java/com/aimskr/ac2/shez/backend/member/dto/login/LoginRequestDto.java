package com.aimskr.ac2.kakao.backend.member.dto.login;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequestDto {
    private String account;
    private String password;

    @Builder
    public LoginRequestDto(String account, String password) {
        this.account = account;
        this.password = password;
    }
}
