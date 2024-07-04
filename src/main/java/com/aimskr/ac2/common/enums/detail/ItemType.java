package com.aimskr.ac2.common.enums.detail;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ItemType {

    // 자동차보험금지급결의서 (CIPS)
    CIPS_INS_COMPANY("CA0001", "insCompany", "자동차보험회사"),
    CIPS_CLAIM_TYPE("CA0002", "claimType", "처리구분"),
    CIPS_INJURY_GUP("CA0003", "injuryGup", "부상급항(급)"),
    CIPS_INJURY_HANG("CA0004", "injuryHang", "부상급항(항)"),
    CIPS_INS_NAME("CA0005", "insName", "피보험자명"),
    // 자동 입력을 위한 항목
    CIPS_INJURY_GRADE("CA0000", "injuryGup", "부상급항"),

    MDDG_DIAGNOSIS("DA0001", "DISEASE_CODE", "진단코드"),
    MDDG_DIAG_MAIN("DA0002", "MAIN_DIAGNOSIS", "주진단여부"),
    MDDG_DIAG_FINAL("DA0003", "FINAL_DIAGNOSIS", "진단구분"),
    MDDG_DIAG_DATE("DA0004", "DIAGNOSIS_DATE", "진단일자"),
    MDDG_DOCTOR_NAME("DA0005", "DOCTOR_NAME", "의사명"),
    MDDG_LICENCE_NO("DA0006", "LICENSE_NO", "면허번호"),

    HSP_TYPE_CODE("HS0001", "hsp_type_code", "요양기관코드"),
    HSP_BIZ_NO("HS0002", "hsp_biz_no", "병원사업자등록번호"),
    HSP_NAME("HS0003", "hsp_biz_name", "의료기관명"),
    HSP_ZIP_CODE("HS0004", "MEDICAL_INSTITUTION_ADDRESS", "의료기관우편번호"),
    HSP_ADDRESS("HS0005", "MEDICAL_INSTITUTION_ADDRESS", "의료기관주소"),
    // 예외처리
    NONE("R0000000", "None", "해당없음");          // 항공기지연

    private String itemCode;
    private String itemNameEng;
    private String itemName;

    ItemType(String itemCode, String itemNameEng, String itemName){
        this.itemCode = itemCode;
        this.itemNameEng = itemNameEng;
        this.itemName = itemName;
    }
    public static String getItemName(String itemCd) {
        return Arrays.stream(ItemType.values())
                .filter(x -> x.itemCode.equals(itemCd))
                .findFirst()
                .orElse(NONE)
                .getItemName();
    }

    public static String getItemCode(String itemName, String prefix){
        return Arrays.stream(ItemType.values())
                .filter(x -> x.name().startsWith(prefix) && x.itemName.equals(itemName))
                .findFirst()
                .orElse(NONE)
                .getItemCode();
    }

}
