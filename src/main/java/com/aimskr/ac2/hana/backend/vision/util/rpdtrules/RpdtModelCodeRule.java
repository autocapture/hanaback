package com.aimskr.ac2.hana.backend.vision.util.rpdtrules;

import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Getter
@Component
public class RpdtModelCodeRule extends ClaimRule {

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_MODEL_CODE);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String modelCode = "";

        Pattern modelCodePattern = Pattern.compile("S[A-Z]-[A-Z0-9]{5,7}");
        Matcher modelCodeMatcher = modelCodePattern.matcher(labelString);

        if (modelCodeMatcher.find()){
            modelCode = modelCodeMatcher.group();
        }

        setItemValue(modelCode);
        setAccuracy(getAccuracy(boxList, modelCode));
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
