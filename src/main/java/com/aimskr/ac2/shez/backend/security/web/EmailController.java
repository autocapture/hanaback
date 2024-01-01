package com.aimskr.ac2.kakao.backend.security.web;


import com.aimskr.ac2.kakao.backend.security.service.EmailService;
import com.aimskr.ac2.kakao.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmailController {
    private final EmailService emailService;
    private final MemberService memberService;

    @PutMapping("/v2/kakao/mail/{account}")
    @ResponseBody
    public void emailConfirm(@PathVariable String account)throws Exception{
        log.debug("post emailConfirm");
        String mailAddress = memberService.checkId(account);
        emailService.sendSimpleMessage(account, mailAddress);
    }
    @PostMapping("/v2/kakao/mail/verifyCode")
    @ResponseBody
    public int verifyCode(String code) {
        log.debug("Post verifyCode");

        int result = 0;
        log.debug("code : "+code);
        log.debug("code match : "+ EmailService.ePw.equals(code));
        if(EmailService.ePw.equals(code)) {
            result =1;
        }

        return result;
    }
}
