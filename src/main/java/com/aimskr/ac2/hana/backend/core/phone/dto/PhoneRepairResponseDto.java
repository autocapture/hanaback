package com.aimskr.ac2.hana.backend.core.phone.dto;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepair;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRepairResponseDto {
    private Long id;
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 파일송수신일련번호
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

    public PhoneRepairResponseDto(PhoneRepair phoneRepair) {
        this.id = phoneRepair.getId();
        this.accrNo = phoneRepair.getAccrNo();
        this.dmSeqno = phoneRepair.getDmSeqno();
        this.fileName = phoneRepair.getFileName();
        this.itemCode = phoneRepair.getItemCode();
        this.itemName = phoneRepair.getItemName();
        this.itemValue = phoneRepair.getItemValue();
        this.accuracy = phoneRepair.getAccuracy();
    }
}
