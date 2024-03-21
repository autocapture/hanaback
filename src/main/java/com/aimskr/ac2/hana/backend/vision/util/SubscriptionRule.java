package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.common.enums.detail.ItemType;

import com.aimskr.ac2.hana.backend.phone_old.dto.Item;
import com.aimskr.ac2.hana.backend.phone_old.dto.Point;
import com.aimskr.ac2.hana.backend.phone_old.dto.Rectangle;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Getter
@Setter
@Component
public abstract class SubscriptionRule extends InputRule {

    private String itemValue;
    private String itemCode;
    private String itemName;
    private Double accuracy;
    private String ruleName;
    private Rectangle rectangle;


    public abstract void applyRule(List<ValueBox> boxList);

    public Item makePhoneItem(){
        return Item.builder()
                .value(itemValue)
                .rectangle(rectangle)
                .confidence(accuracy)
                .build();
    }

    public void setRectangle(int x1, int x2, int y1, int y2){

        Point topLeft = Point.builder()
                .x(x1)
                .y(y1)
                .build();

        Point topRight = Point.builder()
                .x(x2)
                .y(y1)
                .build();

        Point bottomRight = Point.builder()
                .x(x2)
                .y(y2)
                .build();

        Point bottomLeft = Point.builder()
                .x(x1)
                .y(y2)
                .build();

        Rectangle rectangle = Rectangle.builder()
                .topLeft(topLeft)
                .topRight(topRight)
                .bottomRight(bottomRight)
                .bottomLeft(bottomLeft)
                .build();

        setRectangle(rectangle);

    }

    public void setRuleNameByItem(ItemType itemType){
        setRuleName(itemType.getItemName());

    }

    public String timeFormatter(String timeString){
        log.debug("timeString : {}", timeString);

        LocalDate localDateExpDate = LocalDate.parse(timeString);
        LocalDateTime localDateTimeExpDate = localDateExpDate.atStartOfDay();

        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");

        ZonedDateTime zonedDateTimeExpDate = localDateTimeExpDate.atZone(seoulZoneId);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        String formattedDate = formatter.format(zonedDateTimeExpDate);

        return formattedDate;

    }

    public abstract void setItemInfo();

    public void run(List<ValueBox> boxList){
        setRectangle(Rectangle.makeDefault());
        setItemInfo();
        applyRule(boxList);
//        calcAccuracy();
    }


}
