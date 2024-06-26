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

    // 수리비명세서
    RPDT_ISSUE_DATE("RA0001", "issueDate", "발행일자"),
    RPDT_TOTAL_AMOUNT("RA0002", "totalAmount", "총합계"),
    RPDT_MANU_NUM("RA0003", "manufactureNum","제조번호"),
    RPDT_SERIAL_NUM("RA0004", "serialNum","일련번호"),
    RPDT_MODEL_CODE("RA0005", "modelCode","모델코드"),
    RPDT_IMEI("RA0006", "imei","IMEI"),
    RPDT_ITEM_AMOUNT("RA0007","itemAmount", "부품비합계"),
    RPDT_RPR_AMOUNT("RA0008","rprAmount", "수리비합계"),

    // 카드영수증
    RPRC_RECEIVE_DATE("RB0001", "receiveDate", "접수일자"),
    RPRC_ISSUE_DATE("RB0002", "issueDate", "발행일자"),
    RPRC_TOTAL_AMOUNT("RB0003", "totalAmount", "합계금액"),

    // 부가서비스캡쳐
    RPSV_OTHER_INS("RC0001", "otherIns", "타보험여부"),

    // 카드영수증
    BIZ_TEL("M0140000", "bizTel", "전화번호"),
    BIZ_DATE("M0140001", "bizDate", "거래일자"),
    BIZ_TIME("M0140002", "bizTime","거래시간"),
    BIZ_ACCEPTNO("M0140003", "acceptNo","승인번호"),
    BIZ_NO("M0140004", "bizNo","사업자등록번호"),
    BIZ_ADDRESS("M0140005", "address","가맹점주소"),
    BIZ_NAME("M0140006","bizName", "가맹점명"),
    BIZ_TOTAL_AMOUNT("M0140007","totalAmount", "사용금액"),
    BIZ_DISCOUNT("M0140008", "discount", "할인금액"),

    // 항공권
    AIR_AIRPORT("M0150000", "airport", "공항"), // 출발지, 도착지 값 저장을 위한 항목(전송대상 아님)
    AIR_DEPT_DATE("M0150001", "departDate", "출발일자"),
    AIR_DEPT_DATE_ORG("M0150002", "departDateOrg", "출발일자원본"),
    AIR_DEPT_TIME("M0150003", "departTime", "출발시간"),
    AIR_PASSENGER("M0150004", "passengerName", "탑승자명"),
    AIR_FLIGHT_CODE("M0150005", "flightCode", "항공편명"),
    AIR_DEPARTURE("M0150006", "departure", "출발지"),
    AIR_ARRIVAL("M0150007", "arrival", "도착지"),

    // 반품/취소 영수증
    RF_DATE("M0160001", "bizDate", "거래일자"),
    RF_TIME("M0160002", "bizTime","거래시간"),
    RF_ACCEPTNO("M0160003", "acceptNo","승인번호"),
    RF_NO("M0160004", "bizNo","사업자등록번호"),
    RF_ADDRESS("M0160005", "address","가맹점주소"),
    RF_NAME("M0160006","bizName", "가맹점명"),
    RF_TOTAL_AMOUNT("M0160007","totalAmount", "사용금액"),
    RF_PAYMENT("M0160008", "payment", "결제방식"),

    // 외상/기타 영수증
    EC_DATE("M0170001", "bizDate", "거래일자"),
    EC_TIME("M0170002", "bizTime","거래시간"),
    EC_ACCEPTNO("M0170003", "acceptNo","승인번호"),
    EC_NO("M0170004", "bizNo","사업자등록번호"),
    EC_ADDRESS("M0170005", "address","가맹점주소"),
    EC_NAME("M0170006","bizName", "가맹점명"),
    EC_TOTAL_AMOUNT("M0170007","totalAmount", "사용금액"),
    EC_PAYMENT("M0170008", "payment", "결제방식"),

    // 휴대품
    IMEI("imei", "imei", "imei"),
    IMEI2("imei2", "imei2", "imei2"),
    SERIAL_NUM("serialNumber", "serialNumber", "serialNumber"),
    EXP_DATE("LimitedWarranty", "LimitedWarranty", "activatedOn"),
    WARRANTY_ENDED("WarrantyEnded", "WarrantyEnded", "activatedOn"),
    APPLE_CARE("AppleCare", "AppleCare", "activatedOn"),
    FIRSTCALL_DATE("FirstCallDate", "FirstCallDate", "activatedOn"),

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
