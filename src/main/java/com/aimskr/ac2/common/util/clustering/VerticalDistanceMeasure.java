package com.aimskr.ac2.common.util.clustering;

import org.apache.commons.math3.ml.distance.DistanceMeasure;

public class VerticalDistanceMeasure implements DistanceMeasure {

    @Override
    public double compute(double[] a, double[] b) {
//        // Check for vertical line to avoid division by zero
        if (a[0] == b[0]) return Double.POSITIVE_INFINITY;

        // Calculate slope (m) between two points (x1, y1) and (x2, y2)
        // m = (y2 - y1) / (x2 - x1)
        double slope = (b[1] - a[1]) / (b[0] - a[0]);
        double verticalDistance = Math.abs(a[1] - b[1]);
        double horizontalDistance = b[0] - a[0];
        int slopedirection = slope > 0 ? 1 : -1;
        return verticalDistance - horizontalDistance * slope;
//        * slopedirection;

    }
}
