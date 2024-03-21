package com.aimskr.ac2.common.util.clustering;

import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import org.apache.commons.math3.ml.clustering.Clusterable;

public class ValueBoxClusterable implements Clusterable {

    private double[] points;
    private ValueBox valueBox;

    public ValueBoxClusterable(ValueBox valueBox) {
        this.valueBox = valueBox;
        this.points = new double[]{valueBox.getCenterPoint().getX(), valueBox.getCenterPoint().getY()};
    }

    @Override
    public double[] getPoint() {
        return points;
    }

    public ValueBox getValueBox() {
        return valueBox;
    }

}
