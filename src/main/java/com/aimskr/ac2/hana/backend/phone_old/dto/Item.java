package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Item {
    private String value;
    private Rectangle rectangle;
    private Double confidence;

    public void adjustY(int adjustment){

        if (this.rectangle == null){
            return;
        }
        this.rectangle.adjustY(adjustment);
    }

    public void expandX(double minX, double maxX){

        if(this.rectangle ==  null || this.rectangle.checkEmpty()) return;
        this.rectangle.expandX(minX, maxX);

    }

    public void addPadding(int margin){
        if (this.rectangle == null || this.rectangle.checkEmpty()){
            return;
        }
        this.rectangle.addPadding(margin);
    }
}
