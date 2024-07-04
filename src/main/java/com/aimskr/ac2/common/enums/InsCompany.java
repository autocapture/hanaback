package com.aimskr.ac2.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum InsCompany {
    C9200("9200", "KOREAN RE, KOREA")
    , C9201("9201", "하나손해보험")
    , C9202("9202", "한화")
    , C9203("9203", "롯데")
    , C9204("9204", "MG")
    , C9205("9205", "흥국")
    , C9206("9206", "제일")
    , C9207("9207", "리젠트")
    , C9208("9208", "삼성")
    , C9209("9209", "현대")
    , C9210("9210", "KB")

    , C9213("9213", "DB")
    , C9217("9217", "AIG")
    , C9218("9218", "ACE")
    , C9219("9219", "VIGILANT")

    , C9221("9221", "한국해운조합")
    , C9222("9222", "MITUI SUMITIMO, KOREA")
    , C9224("9224", "FEDERAL")
    , C9225("9225", "동경해상")
    , C9229("9229", "MITUI SUMITIMO")

    , C9230("9230", "AXA")
    , C9239("9239", "메리츠")
    , C9240("9240", "그린화재")

    , C9251("9251", "동양생명")
    , C9266("9266", "건설공제조합")
    , C9267("9267", "농협손해보험")
    , C9268("9268", "택시공제")
    , C9269("9269", "버스공제")
    , C9270("9270", "화물공제")

    , C9271("9271", "개인택시공제")
    , C9272("9272", "전세버스공제")
    , C9273("9273", "다음다이렉트")
    , C9274("9274", "현대해상하이카")
    , C9275("9275", "우체국")
    , C9276("9276", "새마을금고")
    , C9277("9277", "녹십자생명")
    , C9278("9278", "우리아비바생명")
    , C9279("9279", "KDB생명")
    , C9280("9280", "미래에셋생명")

    , C9281("9281", "AIA생명")
    , C9282("9282", "신용협동조합중앙회")
    , C9283("9283", "한국교직원공제회")
    , C9284("9284", "농협생명보험")
    , C9285("9285", "캐롯손해보험")

    , C9301("9301", "대한생명")
    , C9302("9302", "알리안츠생명")
    , C9303("9303", "삼성생명")
    , C9304("9304", "흥국생명")

    , C9311("9311", "대신생명")
    , C9316("9316", "신한생명")

    , C9331("9331", "럭키생명")
    , C9333("9333", "금호생명")
    , C9334("9334", "SK생명")
    , C9339("9339", "한일생명")

    , C9351("9351", "DB생명")
    , C9353("9353", "매트라이프생명")
    , C9356("9356", "PCA생명")
    , C9357("9357", "뉴욕생명")

    , C9371("9371", "라이나생명")
    , C9372("9372", "AIG")
    , C9373("9373", "ING생명")
    , C9374("9374", "푸르덴셜생명")
    , C9375("9375", "하나생명")
    , C9376("9376", "CARDIF LIFE INSURANCE")

    , C9400("9400", "MARSH, KOREA")
    , C9501("9501", "교보")

    , C9902("9902", "한국중견기업연합회")
    , C9903("9903", "농협(재공제)")
    , C9905("9905", "수협중앙회")
    , C9907("9907", "한국LP가스공업협회")
    , C9908("9908", "렌터카공제")
    , C9909("9909", "USAA")

    , CH025("H025", "ASIA INSURANCE")
    , CXXX1("XXX1", "무보험")
    , CXXX2("XXX2", "해당무")

    ;

    String code;
    String name;

    InsCompany(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InsCompany findByCode(String code) {
        for (InsCompany value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    public static InsCompany findByName(String name) {
        for (InsCompany value : values()) {
            if (value.name.equals(name)) {
                return value;
            }
        }
        return null;
    }

    public static List<String> getInsCompanyNameList(){

        return Arrays.stream(InsCompany.values())
                .map(InsCompany::getName)
                .collect(Collectors.toList());


    }
}
