package com.aimskr.ac2.hana.backend.channel.service;


import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.channel.json.ResultDto;
import com.aimskr.ac2.hana.backend.core.assign.service.AssignService;
import com.aimskr.ac2.hana.backend.core.assign.service.ClaimProcessManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncService {
    private final SftpService sftpService;
    private final ClaimProcessManager claimProcessManager;
    private final AssignService assignService;

    /**
     * 비동기로 실행되는 요청 처리 과정
     */
    @Async("threadPoolTaskExecutor")
    public void processRequest(ImportDto importDto) {
        log.debug("[AsyncService processingRdRequest] 비동기 SFTP 이미지 처리 : key= {}, value = {}", importDto.getKey(), "value");


        // 1. 요청정보 저장
        assignService.saveAssign(importDto);
        boolean isSuccess = false;

        // 2. SFTP 이미지 다운로드
        isSuccess = sftpService.downloadMultiFile(importDto);
        log.debug("[processRequest] SFTP Result : {}", isSuccess);

        // 3. 이미지 다운로드 성공하면 이미지 처리
        if (isSuccess) {
            log.debug("[processRequest] SFTP 이미지 다운로드 성공 : key= {}, value = {}", importDto.getKey(), "value");
            claimProcessManager.processImages(importDto);
        }
        // 4. 이미지 다운로드가 실패하면 실패 상태로 배당 처리
        else {
            log.error("[processRequest] SFTP 이미지 다운로드 실패 : key= {}, value = {}", importDto.getKey(), "value");
//            assignService.finishWithFtpError(importDto);
        }

    }
    @Async("threadPoolTaskExecutor")
    public void processDupRequest(ImportDto importDto) {
        log.debug("[AsyncService processingRdRequest] 비동기 SFTP 이미지 처리 : key= {}, value = {}", importDto.getKey(), "value");

        String accrNo = importDto.getACD_NO();
        String dmSeqno = importDto.getRCT_SEQ();

        //TODO
        ResultDto resultDto = claimProcessManager.makeSuccessResultDto(accrNo, dmSeqno, "");
        log.debug("[processDupRequest] receiptNo : {}, receiptSeq : {}", accrNo, dmSeqno);
        assignService.finishWithQA(accrNo, dmSeqno, resultDto);


    }

}
