package com.aimskr.ac2.hana.backend.security.service;

import com.aimskr.ac2.hana.backend.member.domain.LoginType;
import com.aimskr.ac2.hana.backend.member.dto.login.UserInfo;
import com.aimskr.ac2.hana.backend.member.service.MemberService;
import com.aimskr.ac2.hana.backend.member.web.MemberController;
import com.aimskr.ac2.hana.backend.security.model.CarrotUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService memberService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Resource
    private UserInfo userInfo;  // 세션 저장용 객체

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // DB에서 읽어온 값

        CarrotUserDetails userDetails = null;
        if (authentication.getPrincipal() instanceof CarrotUserDetails) {
            userDetails = (CarrotUserDetails)authentication.getPrincipal();
        } else {
            // just string
            userDetails = (CarrotUserDetails) memberService.loadUserByUsername(
                    authentication.getPrincipal().toString()
            );
        }
        // 로그인 접속기록 구현
        if (userDetails != null) {
            String account = userDetails.getAccount();
            memberService.updateLoginFailureCnt(account, 0);
            String ipAddr = MemberController.getIpAddress(request);
            String sessionId = request.getSession().getId();
            memberService.saveLoginHistory(account, LoginType.LOGIN, ipAddr, sessionId);

            // session 객체에 저장
            userInfo.setAccount(account);
            userInfo.setName(userDetails.getName());
            userInfo.setIpAddr(ipAddr);
            userInfo.setSessionId(sessionId);
        }


        String auth = SecurityContextHolder.getContext().getAuthentication().toString();

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(userDetails));
    }
}
