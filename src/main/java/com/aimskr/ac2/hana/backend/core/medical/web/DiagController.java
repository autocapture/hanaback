package com.aimskr.ac2.hana.backend.core.medical.web;

import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.KcdResponseDto;
import com.aimskr.ac2.hana.backend.core.medical.service.DiagInfoService;
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
@RequestMapping("/hana/v1/diag")
public class DiagController {

    private final DiagInfoService diagInfoService;

    @Operation(summary = "진단코드 정보 조회", description = "파일명 파라미터로 전달", tags = "PhoneRepair")
    @GetMapping(value="/search")
    public KcdResponseDto searchKcd(@RequestParam String kcdCd) {
        log.debug("[search] kcdCd : {}", kcdCd);
        return diagInfoService.findKcdName(kcdCd);
    }

    @Operation(summary = "진단코드 정보 조회", description = "파일명 파라미터로 전달", tags = "PhoneRepair")
    @GetMapping(value="/get")
    public List<DiagInfoExchangeDto> get(@RequestParam String rqsReqId, @RequestParam String fileName) {
        log.debug("[get] fileName : {}", fileName);
        return diagInfoService.getDiagInfo(rqsReqId, fileName);
    }

}
