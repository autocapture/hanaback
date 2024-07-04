package com.aimskr.ac2.hana.backend.core.medical.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class HospitalResponseDto {

    // 병원코드
    private String hospitalCode;
    // 병원명
    private String hospitalName;
    // 금감원병원구분코드
    private String kfiHospitalCode;
    // 특정병원구분코드
    private String specialHospitalCode;
    // 사업자번호
    private String businessNumber;
    // 우편번호
    private String zipCode;
    // 주소
    private String address;
    // 개업일자
    private String openDate;
    // 폐업일자
    private String closeDate;
    // 병원정비코드
    private String hospitalRepairCode;


}
