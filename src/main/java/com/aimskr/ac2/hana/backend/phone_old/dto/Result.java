package com.aimskr.ac2.hana.backend.phone_old.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ToString
@Builder
public class Result {
    private Item imei;
    private Item serialNumber;
    private Item activatedOn;
    private String activationType;

    public static Result makeSample() {

        Point p1 = Point.builder().x(0.0).y(0.0).build();
        Point p2 = Point.builder().x(0.0).y(1.0).build();
        Point p3 = Point.builder().x(1.0).y(0.0).build();
        Point p4 = Point.builder().x(1.0).y(1.0).build();
        Rectangle rectangle = Rectangle.builder()
                .topRight(p1)
                .topLeft(p2)
                .bottomLeft(p3)
                .bottomRight(p4)
                .build();
        // IMEI 띄워쓰기 협의
        Item imei = Item.builder().value("35 669411 696015 4").rectangle(rectangle).build();
        Item serialNumber = Item.builder().value("G6TDQ3FT0D8X").rectangle(rectangle).build();
        Item activatedOn = Item.builder().value("2023-10-17T21:37:10.000000010Z").rectangle(rectangle).build();
        String activationType = "";

        Result result = Result.builder()
                .imei(imei)
                .serialNumber(serialNumber)
                .activatedOn(activatedOn)
                .activationType("")
                .build();
        return result;
    }

    public static Result makeDefaultResult() {

        Point p1 = Point.builder().x(0.0).y(0.0).build();
        Point p2 = Point.builder().x(0.0).y(0.0).build();
        Point p3 = Point.builder().x(0.0).y(0.0).build();
        Point p4 = Point.builder().x(0.0).y(0.0).build();
        Rectangle rectangle = Rectangle.builder()
                .topRight(p1)
                .topLeft(p2)
                .bottomLeft(p3)
                .bottomRight(p4)
                .build();
        // IMEI 띄워쓰기 협의
        Item imei = Item.builder().value("").rectangle(rectangle).confidence(0.0).build();
        Item serialNumber = Item.builder().value("").rectangle(rectangle).confidence(0.0).build();
        Item activatedOn = Item.builder().value("").rectangle(rectangle).confidence(0.0).build();

        Result result = Result.builder()
                .imei(imei)
                .serialNumber(serialNumber)
                .activatedOn(activatedOn)
                .activationType("None")
                .build();
        return result;
    }

    public void fill(Result other){

        if (!StringUtils.hasText(this.imei.getValue()) && StringUtils.hasText(other.getImei().getValue())) {
            this.imei = other.getImei();
        }
        // 넓이가 더 넓은 것으로 선택
        if (!StringUtils.hasText(this.serialNumber.getValue()) && StringUtils.hasText(other.getSerialNumber().getValue())) {
            this.serialNumber = other.getSerialNumber();
        } else if (StringUtils.hasText(this.serialNumber.getValue()) && StringUtils.hasText(other.getSerialNumber().getValue())) {
            if (other.serialNumber.getRectangle() != null && this.serialNumber.getRectangle() != null &&
                    other.serialNumber.getRectangle().getWidth() > this.serialNumber.getRectangle().getWidth()){
                this.serialNumber = other.getSerialNumber();
            }
        }
        if (!StringUtils.hasText(this.activatedOn.getValue()) && StringUtils.hasText(other.getActivatedOn().getValue())) {
            this.activatedOn = other.getActivatedOn();
        }
        if (this.activationType.equals("None") && StringUtils.hasText(other.getActivationType())) {
            this.activationType = other.getActivationType();
        }
    }

    public void adjustY(int adjustment){

        this.imei.adjustY(adjustment);
        this.serialNumber.adjustY(adjustment);
        this.activatedOn.adjustY(adjustment);

    }

    public boolean checkEmpty(){

        if (this.getImei().getRectangle().checkEmpty() &&
        this.getSerialNumber().getRectangle().checkEmpty() &&
        this.getActivatedOn().getRectangle().checkEmpty()){
            return true;
        }else {
            return false;
        }

    }

//    public void addPadding(int margin){
//        this.imei.addPadding(margin);
//        this.serialNumber.addPadding(margin);
//        this.activatedOn.addPadding(margin);
//    }
//
//    public void normalizeBoxSize(){
//        List<Point> points = new ArrayList<>();
//
//        if (!this.imei.getRectangle().checkEmpty()){
//            points.add(this.imei.getRectangle().getTopLeft());
//            points.add(this.imei.getRectangle().getTopRight());
//        }
//        if (!this.serialNumber.getRectangle().checkEmpty()){
//            points.add(this.serialNumber.getRectangle().getTopLeft());
//            points.add(this.serialNumber.getRectangle().getTopRight());
//        }
//
//        if (!this.activatedOn.getRectangle().checkEmpty()){
//            points.add(this.activatedOn.getRectangle().getTopLeft());
//            points.add(this.activatedOn.getRectangle().getTopRight());
//        }
//
//        if (points.size() > 0){
//            Point minPoint = Collections.min(points, Comparator.comparing(Point::getX));
//            Point maxPoint = Collections.max(points, Comparator.comparing(Point::getX));
//
//            this.imei.expandX(minPoint.getX(), maxPoint.getX());
//            this.serialNumber.expandX(minPoint.getX(), maxPoint.getX());
//            this.activatedOn.expandX(minPoint.getX(), maxPoint.getX());
//        }
//    }


}
