package com.aimskr.ac2.hana.backend.core.phone.dto;

import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneAccd;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepair;
import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
public class PhoneAccdDto {

    private String manufacturer;
    // 표준코드
    private String stdCd;
    // 부품코드
    private String accdCd;
    // 부품명
    private String accdNm;
    // 종류
    private String accdCategory;
    // 심사의견 코드
    private String accdType;
    // 심사의견 명칭
    private String accdTypeDtl;
    // 사유
    private String accdTypeNm;


    public PhoneAccdDto(PhoneAccd phoneAccd) {
        this.manufacturer = phoneAccd.getManufacturer();
        this.stdCd = phoneAccd.getStdCd();
        this.accdCd = phoneAccd.getAccdCd();
        this.accdNm = phoneAccd.getAccdNm();
        this.accdCategory = phoneAccd.getAccdCategory();
        this.accdType = phoneAccd.getAccdType();
        this.accdTypeDtl = phoneAccd.getAccdTypeDtl();
        this.accdTypeNm = phoneAccd.getAccdTypeNm();
    }

    @Builder
    public PhoneAccdDto (String manufacturer, String stdCd, String accdCd, String accdNm, String accdCategory, String accdType, String accdTypeDtl, String accdTypeNm) {
        this.manufacturer = manufacturer;
        this.stdCd = stdCd;
        this.accdCd = accdCd;
        this.accdNm = accdNm;
        this.accdCategory = accdCategory;
        this.accdType = accdType;
        this.accdTypeDtl = accdTypeDtl;
        this.accdTypeNm = accdTypeNm;
    }

    public static PhoneAccdDto buildDefault(){
        return PhoneAccdDto.builder()
                .stdCd("B9999")
                .accdCd("")
                .accdNm("")
                .accdCategory("")
                .accdType("03")
                .accdTypeDtl("판단보류")
                .accdTypeNm("매핑 대상 없음")
                .manufacturer("")
                .build();

    }

    public static PhoneAccdDto buildRepairServiceAccd(){
        return PhoneAccdDto.builder()
                .stdCd("A0001")
                .accdCd("SA0001")
                .accdNm("파손수리 공임비")
                .accdCategory("공임비")
                .accdType("01")
                .accdTypeDtl("부책")
                .accdTypeNm("파손수리 공임비 인정")
                .manufacturer("")
                .build();

    }

    public static PhoneAccdDto buildNonRepairServiceAccd(){
        return PhoneAccdDto.builder()
                .stdCd("A0002")
                .accdCd("SA0002")
                .accdNm("파손수리 외 공임비")
                .accdCategory("공임비")
                .accdType("03")
                .accdTypeDtl("판단보류")
                .accdTypeNm("파손수리 공임비 인정")
                .manufacturer("")
                .build();

    }

}
