package com.aimskr.ac2.hana.backend.core.phone.dto;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepair;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetail;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhoneRepairDetailListDto {
    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 파일송수신일련번호
    private String accrNo;
    // 접수회차
    private String dmSeqno;
    // 파일명
    private String fileName;
    // 항목코드
    private List<PhoneRepairDetailRequestDto> accdList;

}
