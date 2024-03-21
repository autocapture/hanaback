package com.aimskr.ac2.hana.backend.phone_old.web;

import com.aimskr.ac2.hana.backend.member.dto.login.UserInfo;
import com.aimskr.ac2.hana.backend.vision.service.VisionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PhoneController {

    private final VisionService visionService;

    @Resource
    private UserInfo userInfo;  // 세션 저장용 객체

    private static final Logger logger = LoggerFactory.getLogger(PhoneController.class);

    @GetMapping("/v2/hana/phone")
    public void processPhone() {
        visionService.processPhone();
    }
}
