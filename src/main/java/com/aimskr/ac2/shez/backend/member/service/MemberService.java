package com.aimskr.ac2.kakao.backend.member.service;

import com.aimskr.ac2.kakao.backend.member.domain.*;
import com.aimskr.ac2.kakao.backend.member.dto.MemberAssignRequestDto;
import com.aimskr.ac2.kakao.backend.member.dto.MemberRequestDto;
import com.aimskr.ac2.kakao.backend.member.dto.MemberResponseDto;
import com.aimskr.ac2.kakao.backend.member.dto.login.LoginRequestDto;
import com.aimskr.ac2.kakao.backend.member.dto.login.LoginResponseDto;
import com.aimskr.ac2.kakao.backend.security.common.SecurityConst;
import com.aimskr.ac2.kakao.backend.security.common.SecurityUtil;
import com.aimskr.ac2.kakao.backend.security.model.KakaoUserDetails;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Spring Security는 AuthenticationManager가 로그인을 담당하며,
 * UserDetailsService와 PasswordEncoder를 통해 로그인을 관리
 */
@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public static final String ERROR_NOT_EXIST = "NOT_EXIST_ID";    // ID 없음
    public static final String LOGIN_SUCCESS = "SUCCESS";   // 로그인 성공
    public static final String LOGIN_LOCK = "LOCK"; // 비번 5회 오류로 잠김
    public static final String LOGIN_EXPIRED = "EXPIRED"; // 비번 90일 이상 사용

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);


    @Transactional
    public void increaseLoginFailureCnt(String account) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + account));
        int prevCnt = member.getCountFailure() == null ? 0 : member.getCountFailure();
        member.updateCountFailure(prevCnt+1);
    }

    @Transactional
    public void updateLoginFailureCnt(String account, int cnt) {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + account));
        member.updateCountFailure(cnt);
    }

    @Transactional
    public String changePassword(String account, String password) {
        // 비번 변경 대상 사용자
        Member member = memberRepository.findByAccount(account).orElse(null);
        if (member == null) {
            logger.error("사용자 없음 - " + account);
            return "사용자 계정 없음";
        }
        // 직전 패스워드와 동일한지 확인
        if (passwordEncoder.matches(password, member.getPassword())) {
            return "이전에 사용한 패스워드";
        }
        // 패스워드가 유효성 검증
        String checkRes = SecurityUtil.checkValidPassword(password, account, member.getBirth(), member.getPhone());
        // 검증 결과가 SUCCESS라면
        if (checkRes.equals(SecurityConst.VALIDATION_SUCCESS)) {
            // 패스워드 암호화
            member.encodingPassword(passwordEncoder.encode(password));
            return SecurityConst.PASSWORD_CHANGE_SUCCESS;
        }
        // 유효성 검증 결과가 OK가 아니라면,
        else {
            return checkRes;
        }
    }

    @Transactional
    public MemberResponseDto save(MemberRequestDto memberRequestDto) {
        // 신규 사용자
        Member member = memberRequestDto.toEntity();
        // 패스워드 암호화
        member.encodingPassword(passwordEncoder.encode(member.getPassword()));
        // 신규 멤버 초기화 - 비밀번호 잠금 false, 비밀번호 재설정 true, 패스워드 변경 잔여일 90일
        member.setDefaultValue();
        return MemberResponseDto.of(memberRepository.save(member));
    }

    @Transactional
    public MemberResponseDto update(Long id, MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        member.update(memberRequestDto);
        // 패스워드 암호화 (패스워드는 변경하지 않음)
        // member.encodingPassword(passwordEncoder.encode(member.getPassword()));
        return MemberResponseDto.of(member);
    }

    @Transactional
    public void delete (Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        memberRepository.delete(member);
    }

    @Transactional
    public String checkId (String account) {
        Member member = memberRepository.findByAccount(account).orElse(null);
        if(member == null) {
            return SecurityConst.NOT_EXIST_ACCOUNT;
        } else if (member.getEmail() == null || member.getEmail() =="") {
            return SecurityConst.NO_EMAIL_ACCOUNT;
        } else {
            return member.getEmail();
        }
    }

    @Transactional(readOnly = true)
    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return MemberResponseDto.of(member);
    }

    @Transactional
    public void saveLoginHistory(String account, LoginType loginType, String ipAddr, String sessionId) {
        LoginHistory loginHistory = LoginHistory.builder()
                .loginDt(LocalDateTime.now())
                .account(account)
                .loginType(loginType)
                .ipAddr(ipAddr)
                .sessionId(sessionId)
                .build();
        loginHistoryRepository.save(loginHistory);
    }

    @Transactional(readOnly = true)
    public LoginResponseDto doLogin(LoginRequestDto loginRequestDto) {

        LoginResponseDto loginResponseDto = LoginResponseDto.builder().build();
        logger.debug("로그인 요청:" + loginRequestDto.toString());

        try {
            Member member = memberRepository.findByAccount(loginRequestDto.getAccount())
                    .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다. account=" + loginRequestDto.getAccount()));

            if (loginResponseDto == null) { // 사용자 정보 없음
                logger.info("사용자 없음 :" +   loginRequestDto.getAccount());
                throw new UsernameNotFoundException("해당 사용자가 없습니다.");
            } else if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) { // 패스워드 불일치
                logger.info("로그인 패스워드 불일치: 요청 - " + loginRequestDto.getPassword() + ", DB - " + member.getPassword());

                throw new BadCredentialsException("비밀번호가 맞지 않습니다.");
            }  else { // 정상 로그인
                loginResponseDto.setAccount(member.getAccount());
                loginResponseDto.setName(member.getName());
                loginResponseDto.setPassword(member.getPassword());
                loginResponseDto.setRole(member.getRole());
                Boolean isLock = member.getIsLock();
                loginResponseDto.setIsLock(isLock);
                Integer pwdRemainingDay = member.getPwdRemainingDay();
                loginResponseDto.setPwdRemainingDay(pwdRemainingDay);
                String status = MemberService.LOGIN_SUCCESS;
                if (pwdRemainingDay <= 0) status = MemberService.LOGIN_EXPIRED;
                else if (isLock) status = MemberService.LOGIN_LOCK;
                loginResponseDto.setStatus(status);
            }
        } catch(Exception e) {
            loginResponseDto.setAccount(loginRequestDto.getAccount());
            loginResponseDto.setStatus(MemberService.ERROR_NOT_EXIST);
        }
        logger.info("[로그인 결과] " + loginResponseDto.toString());
        return loginResponseDto;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAllDesc() {
        return memberRepository.findAllDesc().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> findByRole(String role) {
        if ("전체".equals(role)) return findAllDesc();

        return memberRepository.findByRole(MemberRole.valueOf(role)).stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * (비밀번호 변경한 날로부터 90일 이후 변경), 잠깐 중지함
     */
    @Transactional
//    @Scheduled(cron = "0 0 0 * * ?", zone = "Asia/Seoul") // 매일 자정
    public void updatePasswordSchedule() {
        for(Member member : memberRepository.findAll()) {
            member.reducePwdRemainingDay();
        }
    }

    /**
     * 스프링 시큐린티 오버라이드, ID를 account로 관리할거라 username -> acocunt로 오버라이딩
     * @param account
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 없습니다. account=" + account));

        return KakaoUserDetails.of(member);
    }

    /**
     * 권한 유형 리턴
     * @param role
     * @return
     */
    private Collection<? extends GrantedAuthority> authorities(MemberRole role) {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    /**
     * QA 배당 여부 수정
     */
    @Transactional
    public String changeIsQaAssign(MemberAssignRequestDto memberAssignRequestDto) {
        Member member = memberRepository.findById(memberAssignRequestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + memberAssignRequestDto.getMemberId()));;
        member.updateIsQaAssign(memberAssignRequestDto.getIsAssign());
        return "SUCCESS";
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getQaMembers() {
        return memberRepository.getQaMembers().stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());
    }

}
