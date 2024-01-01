package com.aimskr.ac2.kakao.backend.security.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
public class SecurityUtil {

    public static String checkValidPassword(String password, String account, LocalDate birth, String phone) {
        // 계정포함 불가
        if (password.contains(account)) {
            log.debug("[비밀번호 오류] 계정이 포함되어 있음 : " + account );
            return "비번에 계정 포함";
        }
        // 생일 포함불가
        String birthday = birth.format(DateTimeFormatter.ofPattern("MMdd"));
        if (password.contains(birthday)) {
            log.debug("[비밀번호 오류] 생일포함되어 있음 : " + birthday );
            return "비번에 생일 포함";
        }
        // 전번 뒷자리 포함불가
        String lastPhoneNumber = phone.substring(phone.length()-4, phone.length());
        if (password.contains(lastPhoneNumber)) {
            log.debug("[비밀번호 오류] 전화번호가 포함되어 있음 : " + lastPhoneNumber );
            return "비번에 전화번호 포함";
        }
        return SecurityConst.VALIDATION_SUCCESS;
    }
    public static Optional<String> getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.debug("Security Context에 인증 정보가 없습니다.");
            return Optional.empty();
        }

        String username = null;
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
            username = springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof String) {
            username = (String) authentication.getPrincipal();
        }

        return Optional.ofNullable(username);
    }
}