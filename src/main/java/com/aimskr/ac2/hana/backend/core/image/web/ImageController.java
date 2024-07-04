package com.aimskr.ac2.hana.backend.core.image.web;


import com.aimskr.ac2.hana.backend.core.image.dto.*;
import com.aimskr.ac2.hana.backend.core.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/image")
public class ImageController {
    private final ImageService imageService;
    @Operation(summary = "접수 이미지 목록 조회", description = "접수번호, 접수회차를 파라미터로 전달", tags = "Image")
    @GetMapping(value="/search")
    public List<ImageResponseDto> search(@RequestParam String rqsReqId, @RequestParam String accrNo, @RequestParam String dmSeqno) {
        log.debug("[search] rqsReqId : {} , receiptNo : {}, receiptSeq : {}", rqsReqId, accrNo, dmSeqno);
        return imageService.search(rqsReqId, accrNo, dmSeqno);
    }

    @Operation(summary = "이미지 정보 조회", description = "파일명 파라미터로 전달", tags = "Image")
    @GetMapping(value="/get")
    public ImageResponseDto get(@RequestParam String rqsReqId, @RequestParam String fileName) {
        log.debug("[get] fileName : {}", fileName);
        return imageService.findByFileName(rqsReqId, fileName);
    }

    @Operation(summary = "이미지 정보 수정 - 항공권", description = "항공권 및 항목값을 전달하여 수정", tags = "Image")
    @PostMapping(value="/update/CIPS")
    public void updateCIPS(@RequestBody ImageDtoCIPS imageDtoCIPS) {
        log.debug("[updateCIPS] ImageDtoCIPS : {}", imageDtoCIPS);
        imageService.updateCIPS(imageDtoCIPS);
    }

    @Operation(summary = "이미지 정보 수정 - 항공권", description = "항공권 및 항목값을 전달하여 수정", tags = "Image")
    @PostMapping(value="/update/DIAG")
    public void updateDIAG(@RequestBody ImageDtoDIAG imageDtoDIAG) {
        log.debug("[updateCIPS] ImageDtoCIPS : {}", imageDtoDIAG);
        imageService.updateDIAG(imageDtoDIAG);
    }

    @Operation(summary = "이미지 정보 수정 - 기타", description = "기타영수증을 전달하여 수정", tags = "Image")
    @PostMapping(value="/update/ETCS")
    public void updateETCS(@RequestBody ImageDtoETCS imageDtoETCS) {
        log.debug("[updateETCS] ImageDtoETCS : {}", imageDtoETCS);
        imageService.updateETCS(imageDtoETCS);
    }
}
