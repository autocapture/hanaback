package com.aimskr.ac2.hana.backend.vision.util.cipsrules;


import com.aimskr.ac2.common.enums.ProcessType;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.domain.DetailKeyword;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Component
public class CipsInjGrdRule extends ClaimRule {

    private final CacheService cacheService;
    @Override
    public void setItemInfo(DocType docType){
            initItem(ItemType.CIPS_INJURY_GRADE);
    }

    // OCR label 전체를 이어붙인 값에서 사업자등록번호 패턴을 탐지한다. (000-00-00000)
    // 사업자 번호 패턴이 확인되지 않을 경우, 각 행에서 숫자 10자리의 패턴을 탐지한다. (0000000000)
    // 간혹 전화번호가 10자리인 경우가 발생하기 때문에 032로 시작하지 않는 숫자 10자리 패턴을 탐지한다.
    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.CIPS_INJURY_GRADE);

        ValueBox identifierBox = boxList.stream().filter(box -> identifier.contains(box.getLabel())).findFirst().orElse(null);
        Pattern pattern = Pattern.compile("([0][1-9]|[1][0-4])[-급]?[0-9]{0,2}항?$");

        if (identifierBox != null){
            int targetRow = identifierBox.getRowId();

            List<ValueBox> targetBoxes = boxList.stream().filter(box -> box.getRowId() - targetRow < 2 && box.getRowId() >= targetRow).toList();

            for (ValueBox targetBox: targetBoxes){

                String targetLabel = targetBox.getLabel();
                if (pattern.matcher(targetLabel).find()){
                    setItemValue(targetLabel);
                    setAccuracy(1.0);
                    return;
                }


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
