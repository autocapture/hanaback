package com.aimskr.ac2.hana.backend.vision.util.mddgrules;


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

@Slf4j
@RequiredArgsConstructor
@Component
public class MddgBizNameRule extends ClaimRule {

    private final CacheService cacheService;
    private String [] bizNameKeyword = {"병원", "의원", "의학", "과"};
    @Override
    public void setItemInfo(DocType docType){
            initItem(ItemType.HSP_NAME);
    }

    // OCR label 전체를 이어붙인 값에서 사업자등록번호 패턴을 탐지한다. (000-00-00000)
    // 사업자 번호 패턴이 확인되지 않을 경우, 각 행에서 숫자 10자리의 패턴을 탐지한다. (0000000000)
    // 간혹 전화번호가 10자리인 경우가 발생하기 때문에 032로 시작하지 않는 숫자 10자리 패턴을 탐지한다.
    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){


        String bizName = "";

        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.HSP_NAME);
        ValueBox bizNameBox = null;

        ValueBox identifierBox = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        if (identifierBox != null) {

            bizNameBox = boxList.stream()
                    .filter(box -> (box.getRowId() == identifierBox.getRowId() || box.getRowId() == identifierBox.getRowId() + 1)  &&
                            (box.getLeft() > identifierBox.getRight() || box.getLeft() == identifierBox.getLeft()) &&
                            Arrays.asList(bizNameKeyword).stream().anyMatch(box.getLabel()::contains))
                    .findFirst()
                    .orElse(null);
            log.debug("[MDDGBizNameRule: bizNameBox: {}", bizNameBox);

        } else{
            log.debug("[MDDGBizNameRule: No identifier box found");
            bizNameBox = boxList.stream()
                    .filter(box -> Arrays.asList(bizNameKeyword).stream().anyMatch(box.getLabel()::endsWith) && !box.getLabel().contains("다."))
                    .findFirst()
                    .orElse(null);
        }

        if (bizNameBox != null){
            bizName = bizNameBox.getLabel();
            bizName = bizName.replace("의료기관명칭", "");
            bizName = bizName.replaceAll("[^가-힣0-9a-zA-Z]", "");
        }

        log.debug("[MDDGBizNameRule: bizName: {}", bizName);

        setItemValue(bizName);
        setAccuracy(getAccuracy(boxList, bizName));

    }

    @Override
    public void calcAccuracy(){

        // API 테스트
    }

    public void calcMidHeight(){

    }
}
