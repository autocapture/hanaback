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

@RequiredArgsConstructor
@Getter
@Component
@Slf4j
public class RpdtSerialNumRule extends ClaimRule {

    private final CacheService cacheService;

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_SERIAL_NUM);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){


        String serialNum = "";

        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.RPDT_SERIAL_NUM);
        ValueBox serialNumIdentifier = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        if (serialNumIdentifier == null) {
            return;
        }


        int serialNumRow = serialNumIdentifier.getRowId();

        Pattern manuPattern = Pattern.compile("[A-Z0-9/]{11}");


        ValueBox targetBox = boxList.stream()
                .filter(box -> (box.getRowId() == serialNumRow) && box.getLeft() >= serialNumIdentifier.getLeft() && manuPattern.matcher(box.getLabel()).find())
                .findFirst()
                .orElse(null);

        if (targetBox == null) return;

        log.debug("targetBox: {}", targetBox.getLabel());

        Matcher serialNumMatcher = manuPattern.matcher(targetBox.getLabel());

        if (serialNumMatcher.find()){
            serialNum = serialNumMatcher.group();
            serialNum = serialNum.replace("O", "0").replace("/", "1");
        }




        setItemValue(serialNum);
        setAccuracy(targetBox.getConfidenceScore());
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
