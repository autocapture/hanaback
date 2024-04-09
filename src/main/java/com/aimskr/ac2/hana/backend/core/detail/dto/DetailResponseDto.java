package com.aimskr.ac2.hana.backend.core.detail.dto;

import com.aimskr.ac2.hana.backend.core.detail.domain.Detail;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DetailResponseDto {
    private Long id;
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 외뢰요청ID
    private String rqsReqId;
    // 접수번호
    private String accrNo;
    // 접수회차
    private String dmSeqno;
    // 파일명
    private String fileName;
    // 항목코드
    private String itemCode;
    // 항목명
    private String itemName;
    // 항목값
    private String itemValue;
    // 정확도
    private Double accuracy;

    public DetailResponseDto(Detail detail) {
        this.id = detail.getId();
        this.rqsReqId = detail.getRqsReqId();
        this.accrNo = detail.getAccrNo();
        this.dmSeqno = detail.getDmSeqno();
        this.fileName = detail.getFileName();
        this.itemCode = detail.getItemCode();
        this.itemName = detail.getItemName();
        this.itemValue = detail.getItemValue();
        this.accuracy = detail.getAccuracy();
    }
}
