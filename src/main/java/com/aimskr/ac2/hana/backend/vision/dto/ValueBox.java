package com.aimskr.ac2.hana.backend.vision.dto;


import com.aimskr.ac2.common.enums.Constant;
import com.synap.ocr.sdk.OCRBox;
import com.synap.ocr.sdk.Point;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;


//표 Header 밑 값들에 대한 OCRBox
@Getter
@Setter
public class ValueBox implements Comparable<ValueBox> {

    private int left;
    private int right;
    private int top;
    private int bottom;
    private double confidenceScore;
    private String label;
    private String header;
    private int rowId = -1;
    private int columnId = -1;
    private int height;

    public ValueBox(int left, int right, int top, int bottom, double confidenceScore, String label) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
        this.confidenceScore = confidenceScore;
        this.label = label;
        this.height = bottom - top;
    }

    public ValueBox(OCRBox ocrBox) {
        this.left = ocrBox.getLeftTop().getX();
        this.right = ocrBox.getRightBottom().getX();
        this.top = ocrBox.getRightTop().getY();
        this.bottom = ocrBox.getLeftBottom().getY();
        this.confidenceScore = ocrBox.getConfidenceScore();
        this.label = ocrBox.getLabel();
        this.height = ocrBox.getLeftBottom().getY() - ocrBox.getLeftTop().getY();
    }

    @Override
    public int compareTo(@NotNull ValueBox o) {
        return Long.compare(this.getLeft(), o.getLeft());
    }

    public boolean isMergeableByCoord(ValueBox other){
        if ("횟수".equals(this.getLabel()) || "본인부담금".equals(this.getLabel()) || "공단부담금".equals(this.getLabel())) return false;
        if (this.overlaps(Constant.HORIZONTAL, Constant.HALF, other) && !this.overlaps(Constant.VERTICAL, Constant.SCRATCH, other) &&
                Math.abs(other.getLeft() - this.getRight()) < this.getHeight()/2){
            return true;
        }else{
            return false;
        }
    }

    public boolean isMergeableByRow(ValueBox other){
        if (this.getRowId() == other.getRowId()) return true;
        else return false;
    }

    public Point getCenterPoint(){
        int xmargin = (this.getRight() - this.getLeft())/2;
        int ymargin = (this.getBottom() - this.getTop())/2;

        int x = this.getLeft() + xmargin;
        int y = this.getTop() + ymargin;

        return new Point(x, y);
    }

    public void join(ValueBox other){
        this.left = Math.min(this.getLeft(), other.getLeft());
        this.right = Math.max(this.getRight(), other.getRight());
        this.top = Math.min(this.getTop(), other.getTop());
        this.bottom = Math.max(this.getBottom(), other.getBottom());
        this.label = this.getLabel() + other.getLabel();
        if (this.getConfidenceScore() == 0 || other.getConfidenceScore() == 0){
            this.confidenceScore = Math.max(this.getConfidenceScore(), other.getConfidenceScore());
        } else{
            this.confidenceScore = Math.min(this.getConfidenceScore(), other.getConfidenceScore());
        }
    }

    // if Y_AXIS overlaps -> same row
    // if X_AXIS overlaps -> same column
    public boolean overlaps(String axis, String mode, ValueBox other){

        double overlappingArea = 0.0;
        if (mode.equals(Constant.HALF)){
            overlappingArea = 0.5;
        }

        int lowerSelf;
        int upperSelf;
        int lowerLimit;
        int upperLimit;

        if (Constant.VERTICAL.equals(axis)){
            lowerSelf = this.getLeft();
            upperSelf = this.getRight();
            lowerLimit = other.getLeft();
            upperLimit = other.getRight();

        } else if (Constant.HORIZONTAL.equals(axis)){

            lowerSelf = this.getTop();
            upperSelf = this.getBottom();
            lowerLimit = other.getTop();
            upperLimit = other.getBottom();

        } else{
            return false;
        }

        if (mode.equals(Constant.SCRATCH)){
            return hasOverlap(lowerSelf, upperSelf, lowerLimit, upperLimit);
        }else {
            return hasOverlap(lowerSelf, upperSelf, lowerLimit, upperLimit)
                    && calculateOverlapPercentage(lowerSelf, upperSelf, lowerLimit, upperLimit) > overlappingArea;
        }
    }

    private boolean hasOverlap(int lowerSelf, int upperSelf, int lowerLimit, int upperLimit) {
        return (lowerSelf >= lowerLimit && lowerSelf < upperLimit)
                || (upperSelf > lowerLimit && upperSelf <= upperLimit)
                || (lowerSelf >= lowerLimit && upperSelf <= upperLimit)
                || (lowerSelf < lowerLimit && upperSelf > upperLimit);
    }

    private double calculateOverlapPercentage(int lowerSelf, int upperSelf, int lowerLimit, int upperLimit){

        double defaultPercentage = 1.0;
        int overlappingLower = Math.max(lowerLimit, lowerSelf);
        int overlappingUpper = Math.min(upperLimit, upperSelf);

        int thisLength = upperSelf - lowerSelf;
        int otherLength = upperLimit - lowerLimit;

        // 더 짧은 것을 기준으로 함
        int baseLength = Math.min(thisLength, otherLength);

        if (baseLength == 0) {
            return defaultPercentage;
        }

        // 비교대상 중 더 짧은 길이를 기준으로 겹쳐지는 길이의 비중을 계산
        double overlappingArea = (double) (overlappingUpper - overlappingLower) / baseLength;

        return overlappingArea;
    }

    public double getSlope(ValueBox other){
        Point thisCenter = this.getCenterPoint();
        Point otherCenter = other.getCenterPoint();

        // rise over run
        double delta = (double)(otherCenter.getY() - thisCenter.getY())/(otherCenter.getX() - thisCenter.getX());

        return delta;
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValueBox v = (ValueBox) o;
        return (this.getLeft() == v.getLeft() && this.getTop() == v.getTop()
                && this.getRight() == v.getRight() && this.getBottom() == v.getBottom());
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("Left:").append(this.getLeft()).append(", ");
        sb.append("Right:").append(this.getRight()).append(", ");
        sb.append("Top:").append(this.getTop()).append(", ");
        sb.append("Bottom:").append(this.getBottom()).append(", ");
        sb.append("Confidence:").append(this.getConfidenceScore()).append(", ");
        sb.append("RowId:").append(this.getRowId()).append(", ");
        sb.append("ColID:").append(this.getColumnId()).append(", ");
        sb.append("Header:").append(this.getHeader()).append(", ");
        sb.append(this.label);
        sb.append("}\n");
        return sb.toString();
    }
}
