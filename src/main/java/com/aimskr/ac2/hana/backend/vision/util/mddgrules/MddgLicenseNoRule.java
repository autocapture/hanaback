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
public class MddgLicenseNoRule extends ClaimRule {

    private final CacheService cacheService;
    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.MDDG_LICENCE_NO);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String licenceNo = "";
        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.MDDG_LICENCE_NO);

        ValueBox identifierBox = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        Pattern licencePattern = Pattern.compile("[0-9]{4,6}");

        if (identifierBox != null) {
            int issueRow = identifierBox.getRowId();

            List<ValueBox> targetBoxes = boxList.stream()
                    .filter(box -> Math.abs(box.getRowId() - issueRow) < 2 && box.getLeft() > identifierBox.getRight())
                    .collect(Collectors.toList());

            if (targetBoxes.size() == 0) return;

            targetBoxes.sort(Comparator.comparingInt(o -> Math.abs(o.getRowId() - issueRow)));

            for (ValueBox tBox : targetBoxes) {

                log.debug("[LicenceRule] tBoxValue {}", tBox.getLabel());
                String tBoxValue = tBox.getLabel();

                String tBoxDigit = tBoxValue.replaceAll("[^0-9]", "");

                Matcher matcher = licencePattern.matcher(tBoxDigit);
                if (matcher.matches()){
                    licenceNo = tBoxDigit;
                    break;
                }
            }
        } else {
            for (String row: rows){

                if (containsAny(row, identifier)){
                    Matcher matcher = licencePattern.matcher(row);
                    if (matcher.find()){
                        licenceNo = matcher.group();
                        break;
                    }
                }
            }
        }
        setItemValue(licenceNo);
        setAccuracy(getAccuracy(boxList, licenceNo));
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }

}
