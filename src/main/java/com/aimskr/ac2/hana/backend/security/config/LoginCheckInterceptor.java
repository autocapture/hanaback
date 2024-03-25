package com.aimskr.ac2.hana.backend.security.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final HttpSession session = request.getSession();
        String path = request.getRequestURI();
        if(path.contains("/hana/v1/login") || path.contains("/hana/v1/logout")) {
            //접근 경로가 로그인, 로그아웃인 경우 체크 예외
            return true;
        } else if (session.getAttribute("scopedTarget.userInfo") == null) {
            //세션 로그인이 없으면 리다이렉트 처리
            response.sendRedirect("/");
            return false;
        } else
            // 세션 로그인이 있으면 권한 점검
            return checkRole(request, session);

    }

    private boolean checkRole(HttpServletRequest request, HttpSession session) {
        return true;
    }
}
