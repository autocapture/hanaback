package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import com.aimskr.ac2.hana.backend.core.image.dto.ImageResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Optional;

import static com.aimskr.ac2.common.enums.detail.ItemType.*;

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
    private String imgDcmTpCd;  // DocType의 Code
    @JsonProperty("IMG_FILE_NM")
    private String imgFileNm;

    @JsonProperty("RCPR_INST_CD")
    private String rcprInstCd; // 요양기관코드
    @JsonProperty("HOSP_BIZ_NO")
    private String hospBizNo; // 병원사업자번호
    @JsonProperty("HOSP_NM")
    private String hospNm; // 병원명
    @JsonProperty("ZPCD")
    private String zpcd; // 우편번호
    @JsonProperty("HOSP_ADR")
    private String hospAdr; // 병원주소

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
                .rcprInstCd("")
                .hospBizNo("")
                .hospNm("")
                .zpcd("")
                .hospAdr("")
                .imgPcsRslCd(imageResponseDto.getImageProcessingResultCode().getCode())
                .build();
    }

    public void updateHspInfo(List<Detail> hspDetails){

        if(hspDetails != null && hspDetails.size() > 0){
            Optional<String> rcprInstCd = hspDetails.stream()
                    .filter(detail -> detail.getItemCode().equals(HSP_TYPE_CODE.getItemCode()))
                    .map(Detail::getItemValue).findFirst();
            Optional<String> hospBizNo = hspDetails.stream()
                    .filter(detail -> detail.getItemCode().equals(HSP_BIZ_NO.getItemCode()))
                    .map(Detail::getItemValue).findFirst();
            Optional<String> hospNm = hspDetails.stream()
                    .filter(detail -> detail.getItemCode().equals(HSP_NAME.getItemCode()))
                    .map(Detail::getItemValue).findFirst();
            Optional<String> zpcd = hspDetails.stream()
                    .filter(detail -> detail.getItemCode().equals(HSP_ZIP_CODE.getItemCode()))
                    .map(Detail::getItemValue).findFirst();
            Optional<String> hospAdr = hspDetails.stream()
                    .filter(detail -> detail.getItemCode().equals(HSP_ADDRESS.getItemCode()))
                    .map(Detail::getItemValue).findFirst();

            String rpInstCdRaw = rcprInstCd.orElse("");
            String rpInstCd = "";

            if (rpInstCdRaw.length() == 8){
                rpInstCd = rpInstCdRaw.substring(4) + rpInstCdRaw.substring(0, 4);
            }

            this.rcprInstCd = rpInstCd;
            this.hospBizNo = hospBizNo.orElse("");
            this.hospNm = hospNm.orElse("");
            this.zpcd = zpcd.orElse("");
            this.hospAdr = hospAdr.orElse("");
        }

    }
}
