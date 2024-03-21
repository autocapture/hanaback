package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MultiItem {

    private String value;
    private Rectangle rectangle;
    private String filename;
    private Double confidence;


    public void rePosition(double ratio){
        this.rectangle.rePosition(ratio);
    }

    public void addPadding(int margin){
        if (this.rectangle == null || this.rectangle.checkEmpty()){
            return;
        }
        this.rectangle.addPadding(margin);
    }

    public static MultiItem of(Item item){
        return MultiItem.builder()
                .value(item.getValue())
                .rectangle(item.getRectangle())
                .confidence(item.getConfidence())
                .filename("")
                .build();
    }

    public static MultiItem makeDefault(){
        return MultiItem.builder()
                .value("")
                .rectangle(Rectangle.makeDefault())
                .confidence(0.0)
                .filename("")
                .build();
    }

}
