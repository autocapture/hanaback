package com.aimskr.ac2.hana.backend.vision.util.mddgrules;


import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Getter
@Component
public class MddgDoctorNameRule extends ClaimRule {

    private final CacheService cacheService;
    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.MDDG_DOCTOR_NAME);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String doctorName = "";

        // 중반 이후에 나옴 -> 처방전은 상단에 나오기 때문에 변경
//        int halfRow = rows.size() / 2;
//
//        List<ValueBox> roiBoxes = boxList.stream()
//                .filter(box -> box.getRowId() > halfRow)
//                .collect(Collectors.toList());

        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.MDDG_DOCTOR_NAME);

        ValueBox identifierBox = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        if (identifierBox != null) {

            log.debug("[MddgDoctorNameRule] identifierBox: {}", identifierBox.getLabel());
            Pattern namePattern = Pattern.compile("(^(김|이|박|최|정|강|조|윤|장|임|류|유|송|서|오)[가-힇]{2})");

            int idRow = identifierBox.getRowId();

            List<ValueBox> targetBoxes = boxList.stream()
                    .filter(box -> Math.abs(box.getRowId() - idRow) < 2)
                    .collect(Collectors.toList());

            targetBoxes.sort(Comparator.comparingInt(box -> Math.abs(box.getRowId() - idRow)));

            if (targetBoxes.size() == 0) return;

            for (ValueBox tBox : targetBoxes) {
                log.debug("[MddgDoctorNameRule] applyRule - tBox: {}", tBox.getLabel());

                Matcher matcher = namePattern.matcher(tBox.getLabel());

                if (matcher.find()){

                    doctorName = matcher.group();
                    break;
                }
            }
        }
        setItemValue(doctorName);
        setAccuracy(getAccuracy(boxList, doctorName));
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
