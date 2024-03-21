package com.aimskr.ac2.common.enums.doc;

import lombok.Getter;

// 서류유형
@Getter
public enum DocType {
    MDRI("medical_receipt_in", "01", "의료비영수증-입원", AccidentType.MEDICAL),
    MDRO("medical_receipt_out", "02", "의료비영수증-통원", AccidentType.MEDICAL),
    MDRC("medicine_receipt", "03", "약국영수증", AccidentType.MEDICAL),

    RPDT("repair_detail", "LI0607001A", "수리비명세서", AccidentType.DAMAGE),
    RPRC("repair_receipt", "LI0607002A", "수리비영수증", AccidentType.DAMAGE),
    RPSV("repair_service", "LI0607006G", "부가서비스내역서", AccidentType.DAMAGE),

    CDRC("card_receipt", "14", "카드영수증", AccidentType.DELAY),
    BDPS("boarding_pass", "15", "항공권", AccidentType.DELAY),
    CDRF("card_refund", "16", "카드취소영수증", AccidentType.COMMON),
    ECRC("etc_receipt", "17", "영수증기타", AccidentType.COMMON),

    MULT("multiple_receipt", "99", "기타", AccidentType.COMMON),
    FLIP("flipped_receipt", "99", "기타", AccidentType.COMMON),


    PHIF("phone_information", "98", "휴대폰청약서류", AccidentType.PHONEINFO),

    ETCS("etc", "99", "기타", AccidentType.COMMON);


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

    public static DocType typeContains(String givenWord, AccidentType accidentType){
        for (DocType docType: values()){
            if (docType.accidentType.equals(accidentType) && docType.docName.contains(givenWord)){
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
}
