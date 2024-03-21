package com.aimskr.ac2.hana.backend.security.common;

import org.springframework.boot.json.GsonJsonParser;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonIdPwAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public JsonIdPwAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        // SpringSucurity 인증에서 가장 먼저 시작되는 부분
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        Map<String, Object> parseJsonMap = parseJsonMap(request);

        String id = (String) parseJsonMap.get("account");
        String pw = (String) parseJsonMap.get("password");
        String birth = (String) parseJsonMap.get("birth");

        request.getSession().setAttribute("birth", birth);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(id, pw);

        // Username과 Token만 넘겨서, KbMemberService - loadUserByUsername() start
        // 1. 멤버가 없으면, UsernameNotFoundException Throw
        // 2. 멤버가 있으면? account, password만 만들어서 doLogin 실행
        //      - 패스워드를 DB에서 가져와서 넣기 때문에 비번 체크하는 방식으로 불일치가 뜨게됨
        //      - 이때 request를 넘기는게 아니므로 birth가 null이 전달됨
        // 3. CustomAuthenticationProvider의 authenticate 에서 인증을 처리해야 함
        // ID, Password로 인증을 수행하고 나서..
        Authentication authenticate = getAuthenticationManager().authenticate(authRequest);

        return authenticate;
    }

    private Map<String, Object> parseJsonMap(HttpServletRequest request) throws IOException {
        String body = request.getReader().lines().collect(Collectors.joining());
        GsonJsonParser gsonJsonParser = new GsonJsonParser();
        return gsonJsonParser.parseMap(body);
    }
}