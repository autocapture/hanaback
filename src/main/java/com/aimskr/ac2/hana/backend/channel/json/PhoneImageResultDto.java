package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.enums.image.ImageProcessingResultCode;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneImageResultDto {

    // 원본파일명 : /images/origin/ 에서 관리
    private String imgId;
    // 서류 유형
    private String imgType;


    private List<ResultItem> items;
    private List<ResultSubItem> accdList;

    public static PhoneImageResultDto of(ImageResponseDto imageResponseDto){
        if (imageResponseDto == null) {
            return null;
        }
        if (imageResponseDto.getDocType() == null) {
            log.error("ImageResultDto - of: {}", imageResponseDto);
            imageResponseDto.setDocType(DocType.ETCS);
        }

        ImageProcessingResultCode imageProcessingResultCode = imageResponseDto.getImageProcessingResultCode();
        String imageProcessingContents = imageProcessingResultCode.getName();
        if (imageProcessingResultCode == null) {
            imageProcessingResultCode = ImageProcessingResultCode.NORMAL;
            // TODO : Throw RuntimeException
            log.error("[of] ImageProcessingResultCode of ImageResultDto is null - {}", imageResponseDto);
        } else if (imageProcessingResultCode.equals(ImageProcessingResultCode.DUPLICATE)){
            imageProcessingContents = imageResponseDto.getDuppedFile();
        }

        return PhoneImageResultDto.builder()
//                .imgId(imageResponseDto.getImgId())
//                .imgType(imageResponseDto.getDocType().getDocCode())
//                .items(null)
//                .accdList(null)
                .build();
    }

}
