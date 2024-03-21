package com.aimskr.ac2.hana.backend.vision.util.rpsvrules;

import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Component
public class RpsvOtherInsRule extends ClaimRule {

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPSV_OTHER_INS);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String otherIns = "false";

        setItemValue(otherIns);
//        setAccuracy(getAccuracy(boxList, otherIns));
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
