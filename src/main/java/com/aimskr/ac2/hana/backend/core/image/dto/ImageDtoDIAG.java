package com.aimskr.ac2.hana.backend.core.image.dto;

import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.SurgInfoExchangeDto;
import lombok.*;

import java.util.List;

/**
 * CIPS (자동차보험금지급내역서) DTO
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDtoDIAG {
    // COMMON
    private String rqsReqId;
    private String accrNo;
    private String dmSeqno;
    private String fileName;
    private String korDocType;
    private String resultCode;

    // DETAIL
    private String hs0001; // 요양기관코드
    private String hs0002; // 사업자등록번호
    private String hs0003; // 병원명
    private String hs0004; // 병원우편번호
    private String hs0005; // 병원주소
    private String da0004; // 진단일
    private String da0005; // 의사명
    private String da0006; // 면허번호
    private String ea0001; // 수술일자

    List<DiagInfoExchangeDto> diagList;
    List<SurgInfoExchangeDto> surgList;

}
