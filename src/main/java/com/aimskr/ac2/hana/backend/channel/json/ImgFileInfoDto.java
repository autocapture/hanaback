package com.aimskr.ac2.hana.backend.channel.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 이미지파일정보
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImgFileInfoDto {
    @Schema(description = "이미지문서번호")
    private String IMG_DCM_NO;
    @Schema(description = "이미지문서철번호")
    private String IMG_DCMFL_NO;
    @Schema(description = "이미지문서ID")
    private String IMG_ID;
    @Schema(description = "이미지파일명")
    private String IMG_FILE_NM;
}
