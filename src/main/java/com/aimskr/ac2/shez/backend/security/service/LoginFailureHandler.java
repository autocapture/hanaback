package com.aimskr.ac2.kakao.backend.security.service;

import com.aimskr.ac2.kakao.backend.member.service.MemberService;
import com.aimskr.ac2.kakao.backend.security.model.LoginFailure;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private final MemberService memberService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String errorMessage = "";
        // incorrect the identity or password
        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다.";
        }
        else if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
            errorMessage = "아이디나 비밀번호가 맞지 않습니다.";
        }
        // account is disabled
        else if (exception instanceof DisabledException) {
            errorMessage = "계정이 비활성화 되었습니다.";
        }
        // expired the credential
        else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "비밀번호 유효기간이 만료 되었습니다.";
        }
        else {
            errorMessage = "로그인에 실패하였습니다. 관리자에게 문의하세요";
        }

        LoginFailure loginFailure = LoginFailure.builder().build();
        loginFailure.setMessage(errorMessage);
        loginFailure.setTimestamp(LocalDateTime.now());

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(loginFailure));

    }
}
