package com.aimskr.ac2.hana.backend.member.web;

import com.aimskr.ac2.hana.backend.member.domain.LoginType;
import com.aimskr.ac2.hana.backend.member.dto.MemberAssignRequestDto;
import com.aimskr.ac2.hana.backend.member.dto.MemberRequestDto;
import com.aimskr.ac2.hana.backend.member.dto.MemberResponseDto;
import com.aimskr.ac2.hana.backend.member.dto.PasswordChangeDto;
import com.aimskr.ac2.hana.backend.member.dto.login.UserInfo;
import com.aimskr.ac2.hana.backend.member.service.MemberService;
import com.aimskr.ac2.hana.backend.security.common.SecurityConst;
import com.aimskr.ac2.hana.backend.security.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/member")
public class MemberController {

    private final MemberService memberService;

    @Resource
    private UserInfo userInfo;  // 세션 저장용 객체

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    @PostMapping("/save")
    public MemberResponseDto save(@RequestBody MemberRequestDto memberRequestDto) {
        return memberService.save(memberRequestDto);
    }

    // 비밀번호 변경
    @PostMapping("/changePass")
    public String changePassword(@RequestBody PasswordChangeDto kbPasswordChangeDto) {
        // 1. code가 일치하지 않으면, 불일치 사유를 안내
        if(!EmailService.ePw.equals(kbPasswordChangeDto.getCode())) {
            return SecurityConst.WORNG_VERIFICATION_CODE;
        }
        // 3. 성공적으로 비번을 변경 했으면, OK 리턴
        else {
            String pwdRes = memberService.changePassword(kbPasswordChangeDto.getAccount(),
                    kbPasswordChangeDto.getPassword());
            if (pwdRes.equals(SecurityConst.PASSWORD_CHANGE_SUCCESS)) {
                return SecurityConst.PASSWORD_CHANGE_SUCCESS;
            }
            // 비번 변경 결과가 OK가 아니라면,  패스워드 정책을 위반했으므로 위반 안내
            else {
                return SecurityConst.PASSWORD_POLICY_VIOLATION + " - " + pwdRes;
            }
        }
    }

    // QA 배당 상태 변경
    @PostMapping("/qaAssign")
    public String changeIsQaAssign(@RequestBody MemberAssignRequestDto memberAssignRequestDto) {
        return memberService.changeIsQaAssign(memberAssignRequestDto);
    }

    @PutMapping("/{id}")
    public MemberResponseDto update(@PathVariable Long id, @RequestBody MemberRequestDto memberRequestDto) {
        return memberService.update(id, memberRequestDto);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (userInfo != null) {
            logger.debug("[LOGOUT] UserInfo : " + userInfo);
            memberService.saveLoginHistory( // 로그아웃 이력
                    userInfo.getAccount(),
                    LoginType.LOGOUT,
                    userInfo.getIpAddr(),
                    userInfo.getSessionId());
        }
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/session")    // 세션정보 확인
    public String get() {
        return userInfo.toString();
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        memberService.delete(id);
        return id;
    }

    @GetMapping("/{id}")
    public MemberResponseDto findById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/role/{role}")
    public List<MemberResponseDto> findByRole(@PathVariable String role) {
        return memberService.findByRole(role);
    }

    @GetMapping("/qa")
    public List<MemberResponseDto> getQaMembers() {
        return memberService.getQaMembers();
    }

    @GetMapping("/list")
    public List<MemberResponseDto> findAll() {
        return memberService.findAllDesc();
    }

    @GetMapping("/check/{account}")
    public String checkId(@PathVariable String account) {
        return memberService.checkId(account);
    }

    public static String getIpAddress(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (!StringUtils.hasText(clientIp)|| "unknown".equalsIgnoreCase(clientIp)) {
            //Proxy 서버인 경우
            clientIp = request.getHeader("Proxy-Client-IP");
        }
        if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            //Weblogic 서버인 경우
            clientIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (!StringUtils.hasText(clientIp) || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        return clientIp;
    }
}
