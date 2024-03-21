package com.aimskr.ac2.hana.backend.channel.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "캐롯손보 요청접수 DTO")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CompleteDto {
    @Schema(description = "의뢰요청ID")
    private String RQS_REQ_ID;
    @Schema(description = "사고번호")
    private String ACD_NO;
    @Schema(description = "접수순번")
    private String RCT_SEQ;
    @Schema(description = "사고일자")
    private String ACD_DT;
    @Schema(description = "하나손보수신결과코드")
    private String RCI_RSL_CD;

}
