package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResultDto {
    @JsonProperty("IMG_DCM_NO")
    private String imgDcmNo;
    @JsonProperty("IMG_DCMFL_NO")
    private String imgDcmflNo;
    @JsonProperty("IMG_ID")
    private String imgId;
    @JsonProperty("IMG_DCM_TP_CD")
    private String imgDcmTpCd;
    @JsonProperty("IMG_FILE_NM")
    private String imgFileNm;
    @JsonProperty("IMG_PCS_RSL_CD")
    private String imgPcsRslCd;
    @JsonProperty("PCS_RSL_LST")
    private List<ResultItem> pcsRslLst;

    public static ImageResultDto of(ImageResponseDto imageResponseDto) {
        return ImageResultDto.builder()
                .imgDcmNo(imageResponseDto.getImgDcmNo() == null? "" : imageResponseDto.getImgDcmNo())
                .imgDcmflNo(imageResponseDto.getImgDcmflNo() == null? "" : imageResponseDto.getImgDcmflNo())
                .imgId(imageResponseDto.getImgId())
                .imgDcmTpCd(imageResponseDto.getDocType().getDocCode())
                .imgFileNm(imageResponseDto.getOriginFileName())
                .imgPcsRslCd(imageResponseDto.getImageProcessingResultCode().getCode())
                .build();
    }
}
