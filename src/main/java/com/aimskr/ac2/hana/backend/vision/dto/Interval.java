package com.aimskr.ac2.hana.backend.vision.dto;

import com.synap.ocr.sdk.Point;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interval {

    private ValueBox startBox;
    private ValueBox endBox;
    private Point startPoint;
    private Point endPoint;
    private double slope;

    public Interval(ValueBox startBox, ValueBox endBox, Point startPoint, Point endPoint, double slope){
        this.startBox = startBox;
        this.endBox = endBox;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.slope = slope;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("StartBox:").append(this.getStartBox().getLabel()).append(", ");
        sb.append("EndBox:").append(this.getEndBox().getLabel()).append(", ");
        sb.append("Start:").append(this.getStartPoint()).append(", ");
        sb.append("End:").append(this.getEndPoint()).append(", ");
        sb.append("Slope:").append(this.getSlope());
        sb.append("}\n");
        return sb.toString();
    }

    public boolean includes(ValueBox box){

        if (box.getRight() >= this.startBox.getLeft() && box.getRight() <= this.endPoint.getX()){
            return true;
        }
        return false;
    }

    public boolean intersects(ValueBox box){

        //StartPoint or Endpoint is within the box
        if (box.getLeft() <= this.startPoint.getX() && this.startPoint.getX() <= box.getRight() &&
                box.getTop() < this.startPoint.getY() && this.startPoint.getY() < box.getBottom()){
            return true;
        }

        if (box.getLeft() <= this.endPoint.getX() && this.endPoint.getX() <= box.getRight() &&
                box.getTop() < this.endPoint.getY() && this.endPoint.getY() < box.getBottom()){
            return true;
        }

        // line penetrates left side of the box
        int run = box.getLeft() - this.startPoint.getX();
        int risedPoint = (int) (run * this.slope + this.startPoint.getY());
        if (box.getLeft() > this.startPoint.getX() && box.getLeft() < this.endPoint.getX() &&
                box.getTop() < risedPoint && box.getBottom() > risedPoint){
            return true;
        }
        return false;
    }

    public Interval adjustYCoord(int adjustment){

        Point newStartPoint = new Point(this.startPoint.getX(), this.startPoint.getY() + adjustment);
        Point newEndPoint = new Point(this.endPoint.getX(), this.endPoint.getY() + adjustment);

        return new Interval(this.startBox, this.endBox, newStartPoint, newEndPoint, this.slope);
    }

}
