package com.aimskr.ac2.hana.backend.member.dto.login;

import com.aimskr.ac2.hana.backend.member.domain.MemberRole;
import lombok.*;

/**
 * UserDetails 인터페이스 구현
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private String account;
    private String name;
    private String password;
    private MemberRole role;
    private String status;
    private Boolean isLock;
    private Integer pwdRemainingDay;
    private String desc;
}
