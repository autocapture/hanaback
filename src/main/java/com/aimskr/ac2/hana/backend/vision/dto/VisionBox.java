package com.aimskr.ac2.hana.backend.vision.dto;

import com.synap.ocr.sdk.OCRBox;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class VisionBox implements Comparable<VisionBox> {
    private OCRBox box;
    private int line;

    public VisionBox(OCRBox box) {
        this.box = box;
    }

    @Override
    public int compareTo(VisionBox thatBox) {
        if (this.box.getRect().getLeft() > thatBox.getBox().getRect().getLeft()) {
            return 1;
        } else if (this.box.getRect().getLeft() < thatBox.getBox().getRect().getLeft()) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisionBox v = (VisionBox) o;

        return  (
            this.box.getLeftTop().getX() == v.getBox().getLeftTop().getX() &&
            this.box.getLeftTop().getY() == v.getBox().getLeftTop().getY() &&
            this.box.getLeftBottom().getX() == v.getBox().getLeftBottom().getX() &&
            this.box.getLeftBottom().getY() == v.getBox().getLeftBottom().getY()
        ) || (
            this.box.getRightTop().getX() == v.getBox().getRightTop().getX() &&
            this.box.getRightTop().getY() == v.getBox().getRightTop().getY() &&
            this.box.getRightBottom().getX() == v.getBox().getRightBottom().getX() &&
            this.box.getRightBottom().getY() == v.getBox().getRightBottom().getY()
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(box);
    }
}
