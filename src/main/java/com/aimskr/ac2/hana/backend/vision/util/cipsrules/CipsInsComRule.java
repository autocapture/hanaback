package com.aimskr.ac2.hana.backend.vision.util.cipsrules;


import com.aimskr.ac2.common.enums.InsCompany;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Component
public class CipsInsComRule extends ClaimRule {

    @Override
    public void setItemInfo(DocType docType){
            initItem(ItemType.CIPS_INS_COMPANY);
    }

    // OCR label 전체를 이어붙인 값에서 사업자등록번호 패턴을 탐지한다. (000-00-00000)
    // 사업자 번호 패턴이 확인되지 않을 경우, 각 행에서 숫자 10자리의 패턴을 탐지한다. (0000000000)
    // 간혹 전화번호가 10자리인 경우가 발생하기 때문에 032로 시작하지 않는 숫자 10자리 패턴을 탐지한다.
    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        List<String> insCompanies = InsCompany.getInsCompanyNameList();

        for (String insCompany: insCompanies){

            String companyName = insCompany;
            if (companyName.length() < 3){
                companyName = insCompany + "(손해|화재|해상)";
            }
            log.debug("companyName: {}", companyName);
            Pattern compPattern = Pattern.compile(companyName);
            if (compPattern.matcher(labelString).find()){
                log.debug("setName: {}", companyName);
                setItemValue(insCompany);
                setAccuracy(1.0);
                return;
            }
        }

    }

    @Override
    public void calcAccuracy(){

        // API 테스트
    }

    public void calcMidHeight(){

    }
}
