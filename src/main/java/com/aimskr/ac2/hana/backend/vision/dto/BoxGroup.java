package com.aimskr.ac2.hana.backend.vision.dto;

import com.aimskr.ac2.common.enums.Constant;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
public class BoxGroup extends ValueBox {

    private String header;
    private String axis;
    List<ValueBox> valueBoxList = new ArrayList<>();

    public BoxGroup(int left, int right, int top, int bottom, double confidenceScore, String label) {
        super(left, right, top, bottom, confidenceScore, label);
    }

    public void spreadHeader(){
        for (ValueBox box: this.valueBoxList){
            box.setHeader(this.getHeader());
        }
    }

    public void spreadRowId(){
        for (ValueBox box: this.valueBoxList){
            box.setRowId(this.getRowId());
        }
    }

    public void spreadColumnId(){
        for (ValueBox box: this.valueBoxList){
            box.setColumnId(this.getColumnId());
        }
    }

    public void mergeBoxesByHeader(){

        List<ValueBox> mergedBoxes = new ArrayList<>();
        List<ValueBox> annexedBox = new ArrayList<>();

        Collections.sort(this.valueBoxList);
        for (int i = 0; i < this.valueBoxList.size(); i ++){
            ValueBox currBox = this.valueBoxList.get(i);

            if (annexedBox.contains(currBox)) continue;
            int j = i + 1;
            while(j < this.valueBoxList.size()){
                ValueBox nextBox = this.valueBoxList.get(j);
                if (currBox.getHeader() != null && currBox.getHeader().equals((nextBox.getHeader()))
//                        && !currBox.overlaps(Constant.VERTICAL, Constant.HALF, nextBox)
                        && currBox.getLabel() != nextBox.getLabel()){
                    currBox.join(nextBox);
                    annexedBox.add(nextBox);
                }
                j++;
            }
            if (!mergedBoxes.contains(currBox)){
                mergedBoxes.add(currBox);
            }
        }

        this.setValueBoxList(mergedBoxes);
    }

    public void mergeBoxesByCoord(){

        List<ValueBox> mergedBoxes = new ArrayList<>();
        List<ValueBox> annexedBox = new ArrayList<>();

        this.valueBoxList.sort(Comparator.comparingInt(ValueBox::getLeft));

        for (int i = 0; i < this.valueBoxList.size(); i ++){
            ValueBox currBox = this.valueBoxList.get(i);

            if (annexedBox.contains(currBox)) continue;
            int j = i + 1;
            while(j < this.valueBoxList.size()){
                ValueBox nextBox = this.valueBoxList.get(j);
                if (currBox.isMergeableByCoord(nextBox) &&
                        !currBox.overlaps(Constant.VERTICAL, Constant.SCRATCH, nextBox) && currBox.getLabel() != nextBox.getLabel()){
                    currBox.join(nextBox);
                    annexedBox.add(nextBox);
                }
                j++;
            }
            if (!mergedBoxes.contains(currBox)){
                mergedBoxes.add(currBox);
            }
        }

        this.setValueBoxList(mergedBoxes);
    }

    public String getGroupedLabel(){
        return this.valueBoxList.stream().map(ValueBox::getLabel).collect(Collectors.joining());
    }

}
