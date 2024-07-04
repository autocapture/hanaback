package com.aimskr.ac2.common.enums.doc;

import com.aimskr.ac2.common.enums.ClaimType;
import lombok.Getter;

// 서류유형
@Getter
public enum DocType {
//    MDRI("medical_receipt_in", "01", "의료비영수증-입원", AccidentType.MEDICAL),
//    MDRO("medical_receipt_out", "02", "의료비영수증-통원", AccidentType.MEDICAL),
//    MDRC("medicine_receipt", "03", "약국영수증", AccidentType.MEDICAL),
//    RPDT("repair_detail", "LI0607001A", "수리비명세서", AccidentType.DAMAGE),
//    RPRC("repair_receipt", "LI0607002A", "수리비영수증", AccidentType.DAMAGE),
//    RPSV("repair_service", "LI0607006G", "부가서비스내역서", AccidentType.DAMAGE),
//    CDRC("card_receipt", "14", "카드영수증", AccidentType.DELAY),
//    BDPS("boarding_pass", "15", "항공권", AccidentType.DELAY),
//    CDRF("card_refund", "16", "카드취소영수증", AccidentType.COMMON),
//    ECRC("etc_receipt", "17", "영수증기타", AccidentType.COMMON),
//    MULT("multiple_receipt", "99", "기타", AccidentType.COMMON),
//    FLIP("flipped_receipt", "99", "기타", AccidentType.COMMON),
//    PHIF("phone_information", "98", "휴대폰청약서류", AccidentType.PHONEINFO),

    MDDG("medical_diagnosis", "0701", "진단서", AccidentType.MEDICAL),
    MDOP("medical_opinion", "0702", "소견서", AccidentType.MEDICAL),
    MDSR("medical_surgery", "0703", "수술확인서", AccidentType.MEDICAL),
    MDIC("medical_confirmation_in", "0705", "입퇴원확인서", AccidentType.MEDICAL),
    MDOC("medical_confirmation_out", "0706", "통원확인서", AccidentType.MEDICAL),
    MDPH("medical_pharmacy", "0708", "처방전", AccidentType.MEDICAL),
    MDEC("medical_diagnosis_etc", "0709", "기타진단명확인서류", AccidentType.MEDICAL),
    SREM("surgery_emergency", "0801", "응급실초진기록", AccidentType.MEDICAL),
    SRRC("surgery_record", "0805", "수술기록지", AccidentType.MEDICAL),

    CIPS("car_insurance_payment_statement", "1001", "자동차보험금지급내역서", AccidentType.PHONEINFO),
    ETCS("etc", "9999", "기타", AccidentType.COMMON);


    private final String docName;
    private final String docCode;
    private final String korName;
    private final AccidentType accidentType;


    DocType(String docName,
            String docCode,
            String korName,
            AccidentType accidentType){
        this.docName = docName;
        this.docCode = docCode;
        this.korName = korName;
        this.accidentType = accidentType;
    }

    public static DocType typeEquals(String givenWord){
        for (DocType docType: values()){
            if (docType.docName.equals(givenWord)){
                return docType;
            }
        }
        return DocType.ETCS;
    }

    public static DocType typeContains(String givenWord){
        for (DocType docType: values()){
            if (docType.docName.contains(givenWord)){
                return docType;
            }
        }
        return DocType.ETCS;
    }

    public static DocType getEnumByKorName(String korName){
        for (DocType docType: values()){
            if (docType.korName.equals(korName)){
                return docType;
            }
        }
        return DocType.ETCS;
    }

    public static DocType findByCode(String code) {
        for (DocType value : values()) {
            if (value.docCode.equals(code)) {
                return value;
            }
        }
        return null;
    }

}
