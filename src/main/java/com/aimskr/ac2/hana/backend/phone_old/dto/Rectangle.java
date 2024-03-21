package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Rectangle {
    private Point topLeft;
    private Point topRight;
    private Point bottomRight;
    private Point bottomLeft;


    public void adjustY(int adjustment){

        this.topLeft.adjustY(adjustment);
        this.topRight.adjustY(adjustment);
        this.bottomRight.adjustY(adjustment);
        this.bottomLeft.adjustY(adjustment);

    }

    public void addPadding(int margin){

        this.topLeft.adjustY(-margin);
        this.topLeft.adjustX(-margin);

        this.topRight.adjustY(-margin);
        this.topRight.adjustX(margin);

        this.bottomRight.adjustY(margin);
        this.bottomRight.adjustX(margin);

        this.bottomLeft.adjustY(margin);
        this.bottomLeft.adjustX(-margin);

    }

    public void expandX(double minX, double maxX){

        this.bottomLeft.setX(minX);
        this.topLeft.setX(minX);
        this.topRight.setX(maxX);
        this.bottomRight.setX(maxX);

    }

    public static Rectangle makeDefault(){
        return Rectangle.builder()
                .topLeft(Point.makeDefault())
                .topRight(Point.makeDefault())
                .bottomLeft(Point.makeDefault())
                .bottomRight(Point.makeDefault())
                .build();
    }

    public boolean checkEmpty(){

        if (this.topLeft.getX() == 0 && this.topLeft.getY() == 0 &&
                this.topRight.getX() == 0 && this.topRight.getY() == 0 &&
                this.bottomRight.getX() == 0 && this.bottomRight.getY() == 0 &&
                this.bottomLeft.getX() == 0 && this.bottomLeft.getY() == 0){
            return true;
        } else{
            return false;
        }

    }

    public double getWidth(){
        return this.topRight.getX() - this.topLeft.getX();
    }

    public void rePosition(double ratio){
        this.topLeft.rePosition(ratio);
        this.topRight.rePosition(ratio);
        this.bottomLeft.rePosition(ratio);
        this.bottomRight.rePosition(ratio);

    }
}
