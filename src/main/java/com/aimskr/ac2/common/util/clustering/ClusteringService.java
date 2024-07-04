package com.aimskr.ac2.common.util.clustering;

import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ClusteringService {

    public List<ValueBox> cluster(List<ValueBox> valueBoxes) {

        List<ValueBox> valueBoxesSorted = new ArrayList<>();

        ValueBox medianBox = valueBoxes.get(valueBoxes.size()/4);
        double epsilon = (medianBox.getHeight())/2;

//        System.err.println("epsilon: " + epsilon);

        DBSCANClusterer<ValueBoxClusterable> clusterer = new DBSCANClusterer<>(epsilon, 0, new VerticalDistanceMeasure());
        List<Cluster<ValueBoxClusterable>> clusters = clusterer.cluster(
                valueBoxes.stream().map(ValueBoxClusterable::new).toList()
        );

        for (int i = 0; i < clusters.size(); i++) {
//            log.debug("Row " + (i + 1) + ":");
            String label = "";
            List<ValueBox> boxesInRow = new ArrayList<>();
            for (ValueBoxClusterable textBox : clusters.get(i).getPoints()) {
                label += textBox.getValueBox().getLabel() + " ";
                textBox.getValueBox().setRowId(i);
                boxesInRow.add(textBox.getValueBox());
            }
            boxesInRow.sort(Comparator.comparingInt(a -> a.getCenterPoint().getX()));
//            log.debug(label);
            valueBoxesSorted.addAll(boxesInRow);
        }

        return valueBoxesSorted;

    }

}
