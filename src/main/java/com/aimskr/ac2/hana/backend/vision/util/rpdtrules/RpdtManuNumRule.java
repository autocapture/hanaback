package com.aimskr.ac2.hana.backend.vision.util.rpdtrules;

import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Component
@Slf4j
public class RpdtManuNumRule extends ClaimRule {

    private final CacheService cacheService;

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_MANU_NUM);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){


        String manuNum = "";

        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.RPDT_MANU_NUM);
        ValueBox serialNumIdentifier = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        if (serialNumIdentifier == null) {
            return;
        }


        int manuNumRow = serialNumIdentifier.getRowId();

        Pattern manuPattern = Pattern.compile("R[A-Z0-9/]{9}");


        ValueBox targetBox = boxList.stream()
                .filter(box -> (box.getRowId() >= manuNumRow) && manuPattern.matcher(box.getLabel()).find())
                .findFirst()
                .orElse(null);

        if (targetBox == null) return;

        log.debug("targetBox: {}", targetBox.getLabel());

        Matcher manuNumMatcher = manuPattern.matcher(targetBox.getLabel());

        if (manuNumMatcher.find()){
            manuNum = manuNumMatcher.group();
            if ((labelString.indexOf("Galaxy") > -1 || labelString.indexOf("SM-") > -1) && !manuNum.startsWith("R"))
                manuNum = "R" + manuNum.substring(1);
            manuNum = manuNum.replace("O", "0").replace("/", "1");
        }




        setItemValue(manuNum);
        setAccuracy(targetBox.getConfidenceScore());
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
