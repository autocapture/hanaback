package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Point {
    private double x;
    private double y;

    public void adjustY(int adjustment){
        this.y = this.y + adjustment;
    }

    public void adjustX(int adjustment){
        this.x = this.x + adjustment;
    }

    public void rePosition(double ratio){

        this.x = this.x * ratio;
        this.y = this.y * ratio;

    }

    public static Point makeDefault(){
        return Point.builder()
                .x(0.0)
                .y(0.0)
                .build();
    }

}
