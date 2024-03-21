package com.aimskr.ac2.hana.backend.core.assign.web;


import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignResponseDto;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.aimskr.ac2.hana.backend.core.assign.service.ClaimProcessManager;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/assign")
public class AssignController {
    private final AssignService assignService;
    private final ClaimProcessManager claimProcessManager;

    @Operation(summary = "입력담당자 조회용", description = "배당할 때 사용하는 API", tags = "Assign")
    @GetMapping("/viewall")
    public List<AssignResponseDto> getAllAssign() {
        return assignService.getAllAssign();
    }

    @Operation(summary = "키값으로 배당 조회", description = "배당 1건을 조회할 때 사용하는 API", tags = "Assign")
    @GetMapping("/get")
    public AssignResponseDto get(@RequestParam String accrNo, @RequestParam String dmSeqno) {
        return assignService.findByKey(accrNo, dmSeqno);
    }

    @Operation(summary = "검색조건으로 배당 조회", description = "검색조건에 해당하는 배당 조회 시 사용하는 API", tags = "Assign")
    @PostMapping("/search")
    public List<AssignResponseDto> search(@RequestBody AssignSearchRequestDto assignSearchRequestDto) {
        return assignService.search(assignSearchRequestDto);
    }

//    @Operation(summary = "검색조건으로 배당 조회", description = "검색조건에 해당하는 배당 조회 시 사용하는 API", tags = "Assign")
//    @GetMapping("/getinputassign")
//    public List<BillingAssignDto> getInputAssign(@RequestParam String fromDate, @RequestParam String toDate) {
//        return assignService.getInputAssign(fromDate, toDate);
//    }

    @Operation(summary = "QA 결과 전송", description = "QA 결과를 전송하는 API", tags = "Assign")
    @GetMapping("/done")
    public void done(@RequestParam String accrNo, @RequestParam String dmSeqno, @RequestParam String apiFlgCd) {
//        assignService.checkDupImageContentsWithinAssign(receiptNo, receiptSeq);
//        assignService.checkDupImageContentsFromAll(receiptNo, receiptSeq);
        log.debug("[done] receiptNo : {}, receiptSeq : {}", accrNo, dmSeqno);
        ResultDto resultDto = claimProcessManager.makeSuccessResultDto(accrNo, dmSeqno, apiFlgCd);
        assignService.finishWithQA(accrNo, dmSeqno, resultDto);
    }
}
