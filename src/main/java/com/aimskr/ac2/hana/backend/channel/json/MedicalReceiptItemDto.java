package com.aimskr.ac2.hana.backend.channel.json;


import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneRepairResponseDto;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalReceiptItemDto {

    // 영수증ID
    private String rtxId;
    // 세부의료비ID
    private String dtMdcsId;
    // 의료비항목코드
    private String mdcsItcd;
    // 급여일부본인부담금액
    private Integer slrPartSeleChamt;
    // 급여공단부담금액
    private Integer slrPartPbcdChamt;
    // 급여전액본인부담금액
    private Integer slrTamtSeleChamt;
    // 비급여선택진료금액
    private Integer nslySlcTrmtamt;
    // 비급여선택진료외금액
    private Integer nslySlcTrmtOutamt;

}
