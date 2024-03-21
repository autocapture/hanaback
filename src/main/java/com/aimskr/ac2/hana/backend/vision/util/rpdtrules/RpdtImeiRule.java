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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Component
@Slf4j
public class RpdtImeiRule extends ClaimRule {


    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_IMEI);
    }

    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString) {

        String imei = "";


        Pattern imeiPattern = Pattern.compile("35[0-9Oo/]{13}");
        Matcher imeiMatcher = imeiPattern.matcher(labelString);

        if (imeiMatcher.find()) {
            imei = imeiMatcher.group();
            log.debug("[IMEIRule] - appyRule: IMEI found {}", imei);
        }


        imei = imei.replace("O", "0").replace("o", "0").replace("/", "1").replace("I", "1");
        imei = imei.replaceAll("[^0-9]", "");


        setItemValue(imei);
//            setRectangle(targetBox.getLeft(), targetBox.getRight(), targetBox.getTop(), targetBox.getBottom());
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
