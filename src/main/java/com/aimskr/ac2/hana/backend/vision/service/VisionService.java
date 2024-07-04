package com.aimskr.ac2.hana.backend.vision.service;

import com.aimskr.ac2.common.enums.Constant;
import com.aimskr.ac2.common.util.FileUtil;
import com.aimskr.ac2.hana.backend.vision.dto.BoxGroup;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.RuleOrganizer;
import com.synap.ocr.sdk.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.aimskr.ac2.hana.backend.vision.util.DocumentTypeChecker;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Data
@RequiredArgsConstructor
@Service
public class VisionService {
    // Vision2
    private final String API_KEY = "SNOCR-2b149fbcc0944fcdaf34c7a987472a5a";
    private final String SERVER_ADDRESS = "http://172.16.213.103:62975";
    private final DocumentTypeChecker documentTypeChecker;
    private final RuleOrganizer ruleOrganizer;

    public List<OCRBox> doOCR(String imagePath, String finalPath) {
        try (OCREngine engine = Engine.createOCREngine(API_KEY, SERVER_ADDRESS)) {
            String skewMode = "image";
            String boxesType = "all";   // Box유형, 기본은 좌표가 변환되지 않은 box사용 - all, raw, block, line, '+'로 조합가능
            String coord = "adjusted";  // 출력 박스 좌표계 - origin : 원본 이미지 기준 좌표, adjusted : 회전 보정된 이미지 기준 좌표
            boolean upload = false;
            String fileId = "";
            boolean formRecognition = false;
            boolean bCopy = false;
            boolean bTextOut = true;
            boolean bSkew = true;
            boolean bCrop = true;
            int pageIndex = 0;
            String regExp = "";
            String langs = "all";
            String maskType = "";
            String outputFormat = "";
            String maskedImagePath = "";
            String formCsvPath = "";
            ROIBox roiBox = null;
            boolean bRemove = false;
            String formIdList = "";


            log.debug("[getOCRBoxes] OCR start - file : {} ", imagePath);
            OCRResult result = Engine.doOCR(imagePath, upload, boxesType, formRecognition, fileId, pageIndex,
                    bCopy, bSkew, bCrop, bTextOut, regExp, langs, coord, maskType, outputFormat, maskedImagePath,
                    finalPath, formCsvPath, roiBox, bRemove, skewMode, formIdList, engine);

            return result.getBoxes();
        } catch (Exception e) {
            log.error("[getOCRBoxes] Synap Exception - exception : {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<OCRBox> doOCRTest(String imagePath) {
        try (OCREngine engine = Engine.createOCREngine(API_KEY, SERVER_ADDRESS)) {
            String skewMode = "image";
            String boxesType = "all";   // Box유형, 기본은 좌표가 변환되지 않은 box사용 - all, raw, block, line, '+'로 조합가능
            String coord = "adjusted";  // 출력 박스 좌표계 - origin : 원본 이미지 기준 좌표, adjusted : 회전 보정된 이미지 기준 좌표
            boolean upload = false;
            String fileId = "";
            boolean formRecognition = false;
            boolean bCopy = false;
            boolean bTextOut = true;
            boolean bSkew = true;
            boolean bCrop = true;
            int pageIndex = 0;
            String regExp = "";
            String langs = "all";
            String maskType = "";
            String outputFormat = "";
            String maskedImagePath = "";
            String finalImagePath = "";
            String formCsvPath = "";
            ROIBox roiBox = null;
            boolean bRemove = false;
            String formIdList = "";

            log.debug("[getOCRBoxes] OCR start - file : {} ", imagePath);
            OCRResult result = Engine.doOCR(imagePath, upload, boxesType, formRecognition, fileId, pageIndex,
                    bCopy, bSkew, bCrop, bTextOut, regExp, langs, coord, maskType, outputFormat, maskedImagePath,
                    finalImagePath, formCsvPath, roiBox, bRemove, skewMode, formIdList, engine);

            return result.getBoxes();
        } catch (Exception e) {
            log.error("[getOCRBoxes] Synap Exception - exception : {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<ValueBox> mergeAndSortClusterBoxes(List<ValueBox> valueBoxList){

        List<ValueBox> finalBoxes = new ArrayList<>();
        List<ValueBox> usedBox = new ArrayList<>();

        valueBoxList.sort(Comparator.comparingInt(ValueBox::getRowId).thenComparingInt(ValueBox::getLeft));

        int k = 0;
        for (int i = 0; i < valueBoxList.size(); i ++){
            ValueBox currBox = valueBoxList.get(i);
            if (usedBox.contains(currBox)) continue;

            List<ValueBox> row = new ArrayList<>();
            row.add(currBox);
            ValueBox baseBox = currBox;
            for (int j = i + 1; j < valueBoxList.size() - 1; j++){
                ValueBox nextBox = valueBoxList.get(j);
                if (baseBox.getRight() < nextBox.getLeft() + 5 && baseBox.overlaps(Constant.HORIZONTAL, Constant.HALF, nextBox)){
                    row.add(nextBox);
                    usedBox.add(nextBox);
                    baseBox = nextBox;
                }
            }

            BoxGroup boxGroup = new BoxGroup(currBox.getLeft(), currBox.getRight(), currBox.getTop(), currBox.getBottom(), 0, "");
            boxGroup.setValueBoxList(row);
            boxGroup.mergeBoxesByCoord();
            boxGroup.setRowId(k);
            boxGroup.spreadRowId();
            finalBoxes.addAll(boxGroup.getValueBoxList());
            k ++;
        }
        return finalBoxes;
    }


    public int getMedianHeight(List<ValueBox> boxList){

        boxList.sort(Comparator.comparingInt(ValueBox::getHeight));
        int middleIdx = boxList.size() / 2;
        ValueBox midBox = boxList.get(middleIdx);
        int boxHeight = midBox.getHeight();

        return boxHeight;
    }

    public List<BoxGroup> makeRowsByCoords(List<ValueBox> boxList){

        List<ValueBox> annexedBoxes = new ArrayList<>();
        List<BoxGroup> rows = new ArrayList<>();

        int k = 0;
        for (int i = 0; i < boxList.size(); i ++){
            ValueBox baseBox = boxList.get(i);
            if (annexedBoxes.contains(baseBox)) continue;
            BoxGroup row = new BoxGroup(baseBox.getLeft(), baseBox.getRight(), baseBox.getTop(), baseBox.getBottom(), 0, "");
            row.getValueBoxList().add(baseBox);
            annexedBoxes.add(baseBox);

//            if (i == boxList.size() - 1){
//                annexedBoxes.add(baseBox);
//                break;
//            }

            for (int j = i + 1; j < boxList.size(); j ++){
                ValueBox nextBox = boxList.get(j);
                if (annexedBoxes.contains(nextBox)) continue;
                if (row.overlaps(Constant.HORIZONTAL, Constant.HALF, nextBox)){
                    row.join(nextBox);
                    row.getValueBoxList().add(nextBox);
                    annexedBoxes.add(nextBox);
                }
            }

            row.setRowId(k);
            row.spreadRowId();
            rows.add(row);
            k ++;

        }

        return rows;
    }

    // VisionTest-s

    public List<ValueBox> toValueBox(List<OCRBox> boxList){

        List<ValueBox> valueBoxList = new ArrayList<>();
        for (OCRBox ocrBox: boxList){
            ValueBox valueBox = new ValueBox(ocrBox);
            valueBoxList.add(valueBox);
        }
        try {
            Collections.sort(valueBoxList, new BoxSorter());
        } catch (Exception e){
            e.printStackTrace();
        }

        return valueBoxList;
    }

    public BoxGroup toBoxGroup(List<ValueBox> valueBoxList){

        if (valueBoxList.size() == 0) return null;
        int minX = valueBoxList.stream().min(Comparator.comparingInt(ValueBox::getLeft))
                .get().getLeft();
        int maxX = valueBoxList.stream().max(Comparator.comparingInt(ValueBox::getRight))
                .get().getRight();
        int minY = valueBoxList.stream().min(Comparator.comparingInt(ValueBox::getTop))
                .get().getTop();
        int maxY = valueBoxList.stream().max(Comparator.comparingInt(ValueBox::getBottom))
                .get().getBottom();

        BoxGroup boxGroup = new BoxGroup(minX, maxX, minY, maxY, 0, "");
        boxGroup.getValueBoxList().addAll(valueBoxList);


        return boxGroup;
    }

    public String makeRawString(List<ValueBox> valueBoxList){

        List<String> stringByRow = mergeLabelsByRow(valueBoxList);

        StringBuilder sb = new StringBuilder();
        for (String row: stringByRow){
            row = row.replace("|", "");
//            row = row + "<br>";
            row = row + "\n";
            sb.append(row);
        }
        return sb.toString();
    }

    public String makeLabelString(List<ValueBox> valueBoxList){

        return valueBoxList.stream()
                .map(box -> box.getLabel())
                .collect(Collectors.joining(""));
    }

    private static Integer getGroupKey(int value) {
        return (value / 5) * 5;
    }

    public List<BoxGroup> valueBoxesToBoxGroup(List<ValueBox> valueBoxList, String groupProperty, int minObjNum){

        Map<Integer, List<ValueBox>> groupMaterials = new HashMap<>();
        String axis = "";
        if (Constant.ROW.equals(groupProperty)){
            axis = Constant.HORIZONTAL;
            groupMaterials = valueBoxList.stream().collect(Collectors.groupingBy(box -> getGroupKey(box.getTop())));
        }else if (Constant.COLUMN.equals(groupProperty)){
            axis = Constant.VERTICAL;
            groupMaterials = valueBoxList.stream().collect(Collectors.groupingBy(w -> w.getLeft()));
        }

        List<BoxGroup> groups = new ArrayList<>();
        for (Integer key: groupMaterials.keySet()){
            groups.add(toBoxGroup(groupMaterials.get(key)));
        }
        List<BoxGroup> finalGroups = new ArrayList<>();
        List<BoxGroup> annexedGroups = new ArrayList<>();
        // Box Group끼리 if y-axis overlap -> merge: 한 Row 의 BoxGroup 생성

        if (Constant.ROW.equals(groupProperty)){
            groups.sort(Comparator.comparingInt(BoxGroup::getTop));
        }else if (Constant.COLUMN.equals(groupProperty)){
            groups.sort(Comparator.comparingInt(BoxGroup::getLeft));
        }

        for (int i = 0; i < groups.size(); i ++){

            BoxGroup boxGroup = groups.get(i);
            if (annexedGroups.contains(boxGroup)) continue;

            int j = i + 1;
            while(j < groups.size()){
                BoxGroup nextBoxGroup = groups.get(j);
                if (boxGroup.overlaps(axis, Constant.SCRATCH, nextBoxGroup)){

                    if (axis.equals(Constant.HORIZONTAL) && boxGroup.overlaps(Constant.VERTICAL, Constant.HALF, nextBoxGroup)){
                        j++;
                        continue;
                    }

                    boxGroup.join(nextBoxGroup);
                    boxGroup.getValueBoxList().addAll(nextBoxGroup.getValueBoxList());
                    annexedGroups.add(nextBoxGroup);

                }
                j++;
            }
            if (boxGroup.getValueBoxList().size() >= minObjNum){
                finalGroups.add(boxGroup);
            }
        }

        return finalGroups;
    }

    public List<String> mergeLabelsByRow(List<ValueBox> boxList){

        boxList.sort(Comparator.comparingInt(ValueBox::getRowId).thenComparingInt(ValueBox::getLeft));


        Map<Integer, StringBuilder> rowIdToStringMap = new HashMap<>();

        for (ValueBox data : boxList) {
            rowIdToStringMap
                    .computeIfAbsent(data.getRowId(), k -> new StringBuilder())
                    .append(data.getLabel());
        }

        List<String> result = new ArrayList<>();
        for (Map.Entry<Integer, StringBuilder> entry : rowIdToStringMap.entrySet()) {
            result.add(entry.getValue().toString());
        }

        return result;
    }

    public List<ValueBox> unpackBoxGroup(List<BoxGroup> boxGroups, String groupProperty){

        List<ValueBox> valueBoxes = new ArrayList<>();
        for (BoxGroup boxGroup: boxGroups){
            int idx = boxGroups.indexOf(boxGroup);
            if (Constant.ROW.equals(groupProperty)){
                boxGroup.setRowId(idx);
                boxGroup.spreadRowId();
            }else if (Constant.COLUMN.equals(groupProperty)){
                boxGroup.setColumnId(idx);
                boxGroup.spreadColumnId();
            }
            valueBoxes.addAll(boxGroup.getValueBoxList());

        }
        return valueBoxes;
    }

    public class BoxSorter implements Comparator<ValueBox> {
        @Override
        public int compare(ValueBox o1, ValueBox o2) {
            // First, compare by the top position
            int topCompare = Integer.compare(o1.getTop(), o2.getTop());
            if (topCompare != 0) {
                return topCompare;
            }

            // If tops are identical, compare by the left position
            return Integer.compare(o1.getLeft(), o2.getLeft());
        }
    }

}
