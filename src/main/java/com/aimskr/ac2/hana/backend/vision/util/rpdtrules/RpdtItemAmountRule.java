package com.aimskr.ac2.hana.backend.vision.util.rpdtrules;

import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.service.PhoneDetailAutoInputService;
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
public class RpdtItemAmountRule extends ClaimRule {

    private final CacheService cacheService;
    private final PhoneDetailAutoInputService phoneDetailAutoInputService;

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_ITEM_AMOUNT);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String itemAmount = "";

        setItemValue(itemAmount);
        setAccuracy(getAccuracy(boxList, itemAmount));
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
