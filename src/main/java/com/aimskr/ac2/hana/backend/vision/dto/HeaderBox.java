package com.aimskr.ac2.hana.backend.vision.dto;

import com.synap.ocr.sdk.OCRBox;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HeaderBox {
    int line;
    private OCRBox box;

    public HeaderBox(int line, OCRBox box) {
        this.line = line;
        this.box = box;
    }
}
