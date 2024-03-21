package com.aimskr.ac2.common.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KeySearchDto {
    private String accrNo;
    private String dmSeqno;

    public String getKey() {
        return accrNo + "/" + dmSeqno;
    }
}
