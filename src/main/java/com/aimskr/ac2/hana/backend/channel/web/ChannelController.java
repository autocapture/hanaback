package com.aimskr.ac2.hana.backend.channel.web;


import com.aimskr.ac2.hana.backend.channel.json.AcceptResultDto;
import com.aimskr.ac2.hana.backend.channel.json.CompleteDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.service.AsyncService;
import com.aimskr.ac2.common.enums.status.AcceptStatus;
import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/")
public class ChannelController {
    public static final String PHONE_REPAIR = "P";
    public static final String MED = "M";
    public static final String CHECK = "check";

    private final AsyncService asyncService;
    private final AssignService assignService;
    private final Gson gson;

    @Operation(summary = "요청접수", description = "AIMS에 처리를 요청하는 API", tags = "Channel")
    @PostMapping("/order")
    public ResponseEntity<AcceptResultDto> save(@RequestBody ImportDto importDto) {
        log.info("[save] /order - importDto : {},", gson.toJson(importDto));

        try {
            String validation = importDto.checkValid();
            if (!validation.equals(ImportDto.VALID)) {
                log.error("[save] /order - invalid : {}", validation);
                return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
            }

            boolean duplicity = assignService.checkDupAssign(importDto);
//            boolean duplicity = false;
            // 중복요청일 경우, 배당을 하지 않고 INVALID로 응답
            if (duplicity){
                asyncService.processDupRequest(importDto);
                return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
            }
//            if (duplicity){
//                log.error("[save] /order - duplicate : {}", true);
//                return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.DUPLICATE));
//            }
            else if (validation.equals(ImportDto.VALID) && !duplicity) {
                log.debug("[save] validation success - processRequest start");
                asyncService.processRequest(importDto);
                return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
            } else {
                log.error("[save] /order - invalid : {}", validation);
//                assignService.finishWithValidationError(importDto);
                return ResponseEntity.badRequest().body(AcceptResultDto.of(AcceptStatus.INVALID));
            }
        } catch (Exception e) {
            log.error("[save] /order - error : {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(AcceptResultDto.of(AcceptStatus.ERROR));
        }

    }

    @Operation(summary = "요청접수-qa", description = "AIMS에 처리를 요청하는 API (qa용)", tags = "Channel")
    @PostMapping("/check")
    public ResponseEntity<AcceptResultDto> check(@RequestBody ImportDto importDto) {
        log.info("[check] /order - importDto : {},", gson.toJson(importDto));

        try {
            String validation = importDto.checkValid();
            if (validation.equals(ImportDto.VALID)) {
                log.debug("[check] validation success - processRequest start");
                asyncService.processRequest(importDto);
                return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.OK));
            } else {
                log.error("[check] /order - invalid : {}", validation);
                assignService.finishWithValidationError(importDto);
                return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.INVALID));
            }
        } catch (Exception e) {
            log.error("[check] /order - error : {}", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.ok().body(AcceptResultDto.of(AcceptStatus.ERROR));
        }
    }

    @Operation(summary = "처리결과접수", description = "AIMS의 처리결과 수신후 수신결과를 알려주는 API", tags = "Channel")
    @PostMapping("/complete")
    public ResponseEntity<AcceptResultDto> complete(@RequestBody CompleteDto completeDto) {
        log.info("[complete] /complete - {}", gson.toJson(completeDto));
        assignService.complete(completeDto);
        AcceptResultDto acceptResultDto = AcceptResultDto.builder().status("200").message("OK").build();
        return ResponseEntity.ok().body(acceptResultDto);
    }
}
