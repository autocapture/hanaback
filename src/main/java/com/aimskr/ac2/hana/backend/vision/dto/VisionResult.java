package com.aimskr.ac2.hana.backend.vision.dto;

import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisionResult {
    // 입력대상 여부
    boolean isInputRequired;
    // OCR결과 문자열
    String content;
    // 예외 발생 여부
    boolean isError;
    boolean isQa;
    DocType docType;

    public static VisionResult createInitialResult() {
        return VisionResult.builder()
                .isInputRequired(true)
                .isError(false)
                .isQa(false)
                .content("")
                .build();
    }
}
