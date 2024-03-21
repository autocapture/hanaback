package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Getter
@Setter
@Component
public abstract class InputRule {

    private String itemCode;
    private String itemName;
    private String itemValue;
    private Double accuracy;

    public void initItem(ItemType itemType){
        setItemCode(itemType.getItemCode());
        setItemName(itemType.getItemName());
        setAccuracy(0.0);
        setItemValue("");
    }

    public double getAccuracy(List<ValueBox> boxList, String target){

        if (!StringUtils.hasText(target)){
            log.debug("getAccuracy: target is empty");
            return 0.0;
        }

        ValueBox targetBox = boxList.stream()
                .filter(box -> box.getLabel().length()> 1 &&
                        (box.getLabel().toUpperCase().replace(",", "").contains(target) ||
                        target.contains(box.getLabel().toUpperCase().replace(",", ""))))
                .max(Comparator.comparingInt(vbox -> vbox.getLabel().length()))
                                .orElse(null);
        if (targetBox == null) return 0.6;

        log.debug("getAccuracy: target: {}", target);
        log.debug("getAccuracy: targetBox: {}", targetBox.getLabel());

        return Math.round(targetBox.getConfidenceScore() * 100) / 100.0;
    }


    public void addAccuracy(double addition){
        setAccuracy(Math.min(1.0, Math.round((getAccuracy() + addition) * 100) / 100.0));
    }
}
