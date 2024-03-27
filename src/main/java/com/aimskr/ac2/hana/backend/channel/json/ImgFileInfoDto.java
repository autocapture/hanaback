package com.aimskr.ac2.hana.backend.channel.json;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("IMG_DCM_NO")
    private String imgDcmNo;
    @JsonProperty("IMG_DCMFL_NO")
    private String imgDcmflNo;
    @JsonProperty("IMG_ID")
    private String imgId;
    @JsonProperty("IMG_FILE_NM")
    private String imgFileNm;
}
