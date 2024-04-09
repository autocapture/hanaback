package com.aimskr.ac2.hana.backend.core.detail.web;

import com.aimskr.ac2.hana.backend.core.detail.dto.DetailResponseDto;
import com.aimskr.ac2.hana.backend.core.detail.service.DetailService;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageSearchRequestDto;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageSingleSearchRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/hana/v1/detail")
public class DetailController {
    private final DetailService detailService;

    @Operation(summary = "상세값을 파일명으로 조회", description = "인식/입력된 상세값을 조회하는 API", tags = "Detail")
    @GetMapping(value="/get")
    public List<DetailResponseDto> get(@RequestParam String fileName) {
        return detailService.findByFileName(fileName);
    }

    // Key값으로 조회
    @Operation(summary = "상세값을 Key로 조회", description = "인식/입력된 상세값을 조회하는 API", tags = "Detail")
    @PostMapping(value="/findByKey")
    public List<DetailResponseDto> findByKey(@RequestBody ImageSearchRequestDto imageSearchRequestDto) {
        return detailService.findByKey(imageSearchRequestDto);
    }

    // Key값 + 파일명으로 조회
    @Operation(summary = "상세값을 Key, 파일명으로 조회", description = "인식/입력된 상세값을 조회하는 API", tags = "Detail")
    @PostMapping(value="/findByKeyAndFileName")
    public List<DetailResponseDto> findByFileName(@RequestBody ImageSingleSearchRequestDto kbImageSingleSearchRequestDto) {
        return detailService.findByKeyAndFileName(kbImageSingleSearchRequestDto);
    }
}
