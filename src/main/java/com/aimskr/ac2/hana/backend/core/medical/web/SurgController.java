package com.aimskr.ac2.hana.backend.core.medical.web;

import com.aimskr.ac2.hana.backend.core.medical.dto.SurgInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.service.SurgInfoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/surg")
public class SurgController {

    private final SurgInfoService surgInfoService;

    @Operation(summary = "수술 정보 조회", description = "파일명 파라미터로 전달", tags = "SurgInfo")
    @GetMapping(value="/get")
    public List<SurgInfoExchangeDto> get(@RequestParam String rqsReqId, @RequestParam String fileName) {
        log.debug("[get] fileName : {}", fileName);
        return surgInfoService.getSurgInfo(rqsReqId, fileName);
    }

}
