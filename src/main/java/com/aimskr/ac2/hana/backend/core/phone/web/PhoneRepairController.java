package com.aimskr.ac2.hana.backend.core.phone.web;


import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageDtoETCS;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageDtoRPDT;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageDtoRPRC;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairDetailListDto;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairDetailResponseDto;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairResponseDto;
import com.aimskr.ac2.hana.backend.core.phone.service.PhoneRepairService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/phoneRepair")
public class PhoneRepairController {

    private final PhoneRepairService phoneRepairService;

    @Operation(summary = "폰케어 입력 정보 조회", description = "파일명 파라미터로 전달", tags = "PhoneRepair")
    @GetMapping(value="/get")
    public List<PhoneRepairResponseDto> get(@RequestParam String accrNo,@RequestParam String dmSeqno, @RequestParam String fileName) {
        log.debug("[get] accrNo : {}, dmSeqno : {}, fileNmae: {}", accrNo, dmSeqno, fileName);
        return phoneRepairService.findByKeyAndFileName(accrNo, dmSeqno, fileName);
    }

    @Operation(summary = "폰케어 입력 정보 조회", description = "파일명 파라미터로 전달", tags = "PhoneRepair")
    @GetMapping(value="/getRepairDetail")
    public List<PhoneRepairDetailResponseDto> getDetail(@RequestParam String accrNo, @RequestParam String dmSeqno, @RequestParam String fileName) {
        log.debug("[getDetail] accrNo : {}, dmSeqno : {}, fileNmae: {}", accrNo, dmSeqno, fileName);
        return phoneRepairService.getPhoneRepairDetail(accrNo, dmSeqno, fileName);
    }

    @Operation(summary = "폰케어 부품 입력", description = "파일명 파라미터로 전달", tags = "PhoneRepair")
    @PostMapping(value="/saveDetail")
    public void saveDetail(@RequestBody PhoneRepairDetailListDto phoneRepairDetailListDto) {
        log.debug("[saveDetail] PhoneRepairDetailListDto : {}", phoneRepairDetailListDto);
        phoneRepairService.savePhoneRepairDetail(phoneRepairDetailListDto);
    }

}
