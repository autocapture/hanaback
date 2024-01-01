package com.aimskr.ac2.kakao.backend.member.dto;

import lombok.*;

/**
 * 비밀번호 변경 요청 DTO
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDto {
    String account;
    String password;
    String code;
}
