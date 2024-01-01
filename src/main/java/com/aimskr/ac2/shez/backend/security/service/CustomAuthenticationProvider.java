package com.aimskr.ac2.kakao.backend.security.service;

import com.aimskr.ac2.kakao.backend.member.service.MemberService;
import com.aimskr.ac2.kakao.backend.security.model.KakaoUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/**
 * 실질적인 인증처리를 수행함
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final HttpSession httpSession;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // DB에서 읽어온 값
        KakaoUserDetails userDetails = (KakaoUserDetails)memberService.loadUserByUsername(username);

        // 계정이 잠겼으면
        if (userDetails.getIsLock()) {
            log.debug("계정이 잠겼습니다.");
            throw new DisabledException("계정이 잠겼습니다.");
        }

        // 첫번째 인자는 raw, 두번째 인자는 encoded
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            memberService.increaseLoginFailureCnt(username);
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails, userDetails.getAuthorities());
    }

    // 커스터마이징한 Authentication Token을 사용하지 않으므로 supports true
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}