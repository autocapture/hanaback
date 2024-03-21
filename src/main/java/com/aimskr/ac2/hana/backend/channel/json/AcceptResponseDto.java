package com.aimskr.ac2.hana.backend.channel.json;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcceptResponseDto {
    // 응답코드
    // 200: 정상접수, 300: 이미지오류, 400: 요청전문오류, 500: 접수처리오류


    // 접수번호
    private String accrNo;
    // 접수회차
    private String dmSeqno;
    // 사고유형
    private String accidentType;
    // 피보험자명
    private String nrdNm;
    // 호출시간
    private String rqstTime;
    // 접수시간
    private String acceptTime;
    // 접수결과코드
    private String acceptResponseCode;

    private List<ImageAcceptResponse> images;

}
