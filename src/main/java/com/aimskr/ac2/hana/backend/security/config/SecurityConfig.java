package com.aimskr.ac2.hana.backend.security.config;

import com.aimskr.ac2.hana.backend.member.service.MemberService;
import com.aimskr.ac2.hana.backend.security.common.JsonIdPwAuthenticationFilter;
import com.aimskr.ac2.hana.backend.security.service.CustomAuthenticationProvider;
import com.aimskr.ac2.hana.backend.security.service.LoginFailureHandler;
import com.aimskr.ac2.hana.backend.security.service.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationProvider authenticationProvider;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailureHandler loginFailureHandler;

    private static final RequestMatcher LOGIN_REQUEST_MATCHER = new AntPathRequestMatcher("/hana/v1/login","POST");

    // 세션 클러스터링 환경 대응을 위한 리스터 등록
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    // CustomAuthenticationProvider 설정
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider)
                .userDetailsService(memberService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JsonIdPwAuthenticationFilter jsonAuthenticationFilter = new JsonIdPwAuthenticationFilter(LOGIN_REQUEST_MATCHER);
        jsonAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        // SuccessHandler
        jsonAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        // FailureHandler
        jsonAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);

        // CSRF 사용안함
        http.csrf().disable();

        // http.formLogin(); 을 사용하면 시큐리티에서는 기본적으로 UsernamePasswordAuthenticationFilter 을 사용
        http.addFilterAt(jsonAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                // URL 인증여부
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/hana/v1/member/changePass").permitAll()
                .antMatchers(HttpMethod.GET, "/hana/v1/member/check/**").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/mail/verifyCode").permitAll()
                .antMatchers(HttpMethod.PUT, "/hana/v1/mail/**").permitAll()
                .antMatchers(HttpMethod.GET, "/hana/v1/image/searh").permitAll()
                .antMatchers(HttpMethod.GET, "/hana/v1/image/get").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/member").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/login").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/order").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/check").permitAll()
                .antMatchers(HttpMethod.POST, "/actuator/prometheus").permitAll()
                .antMatchers(HttpMethod.POST, "/actuator/health").permitAll()
                .antMatchers(HttpMethod.GET, "/hana/v1/assign/done").permitAll()
                .antMatchers(HttpMethod.GET, "/health").permitAll()
                .antMatchers(HttpMethod.POST, "/hello").permitAll()
                .antMatchers(HttpMethod.GET, "/stress").permitAll()
                .antMatchers(HttpMethod.POST, "/hana/v1/complete").permitAll()
                .antMatchers(HttpMethod.GET, "/hana/v1/phone").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/swagger*/**").permitAll()
                .anyRequest().authenticated()
                .and()
                // 중복 로그인 방지 maxSessionPrevents가 true이면, 요청 사용자의 인증을 실패, false면 기존 사용자의 세션을 만료
                .sessionManagement()
                .maximumSessions(-1)    // 무한 설정
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/duplicated-login")
                .sessionRegistry(sessionRegistry());
    }
}
