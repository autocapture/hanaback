package com.aimskr.ac2.hana.backend.core.assign.web;


import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.channel.service.AsyncService;
import com.aimskr.ac2.hana.backend.core.assign.domain.Assign;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignResponseDto;
import com.aimskr.ac2.hana.backend.core.assign.dto.AssignSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.aimskr.ac2.hana.backend.core.assign.service.ClaimProcessManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
    private final AsyncService asyncService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Operation(summary = "입력담당자 조회용", description = "배당할 때 사용하는 API", tags = "Assign")
    @GetMapping("/viewall")
    public List<AssignResponseDto> getAllAssign() {
        return assignService.getAllAssign();
    }

    @Operation(summary = "키값으로 배당 조회", description = "배당 1건을 조회할 때 사용하는 API", tags = "Assign")
    @GetMapping("/get")
    public AssignResponseDto get(@RequestParam String rqsReqId, @RequestParam String accrNo, @RequestParam String dmSeqno) {
        return assignService.findByKey(rqsReqId, accrNo, dmSeqno);
    }

    @Operation(summary = "검색조건으로 배당 조회", description = "검색조건에 해당하는 배당 조회 시 사용하는 API", tags = "Assign")
    @PostMapping("/search")
    public List<AssignResponseDto> search(@RequestBody AssignSearchRequestDto assignSearchRequestDto) {
        return assignService.search(assignSearchRequestDto);
    }

    @PutMapping("/retry/{id}")
    public void downloadRetry(@PathVariable Long id) {
        AssignResponseDto assignResponseDto = assignService.findById(id);
        log.debug("[downloadRetry] assignResponseDto : {}", assignResponseDto);
        if (assignResponseDto != null) {
            ImportDto importRdDto = null;
            try {
                importRdDto = objectMapper.readValue(assignResponseDto.getRequestJson(), ImportDto.class);
                log.debug("[downloadRetry] importRdDto : {}", importRdDto);
                asyncService.retry(importRdDto);
            } catch (JsonProcessingException e) {
                log.error("[downloadRetry] importRdDto parsing error : {}", assignResponseDto.getRequestJson()  );
                e.printStackTrace();
            }
        }
    }

//    @Operation(summary = "검색조건으로 배당 조회", description = "검색조건에 해당하는 배당 조회 시 사용하는 API", tags = "Assign")
//    @GetMapping("/getinputassign")
//    public List<BillingAssignDto> getInputAssign(@RequestParam String fromDate, @RequestParam String toDate) {
//        return assignService.getInputAssign(fromDate, toDate);
//    }

    @Operation(summary = "QA 결과 전송", description = "QA 결과를 전송하는 API", tags = "Assign")
    @GetMapping("/done")
    public void done(@RequestParam String rqsReqId, @RequestParam String accrNo, @RequestParam String dmSeqno) {
//        assignService.checkDupImageContentsWithinAssign(receiptNo, receiptSeq);
//        assignService.checkDupImageContentsFromAll(receiptNo, receiptSeq);
        log.debug("[done] receiptNo : {}, receiptSeq : {}", accrNo, dmSeqno);
        ResultDto resultDto = claimProcessManager.makeSuccessResultDto(rqsReqId, accrNo, dmSeqno);
        assignService.finishWithQA(rqsReqId, accrNo, dmSeqno, resultDto);
    }
}
