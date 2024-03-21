package com.aimskr.ac2.hana.backend.vision.service;

import com.aimskr.ac2.common.enums.Constant;
import com.aimskr.ac2.hana.backend.phone_old.domain.*;
import com.aimskr.ac2.hana.backend.vision.dto.BoxGroup;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.PhoneExtractor;
import com.aimskr.ac2.hana.backend.vision.util.RuleOrganizer;
import com.synap.ocr.sdk.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.aimskr.ac2.hana.backend.vision.util.DocumentTypeChecker;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
    private final PhoneRepository phoneRepository;
    private final CarrotFileRepository carrotFileRepository;
    private final PhoneExtractor phoneExtractor;

    @Async
    public void processPhone() {
        log.info("processPhone");
        doAutoInput();
    }

    public void doAutoInput() {
        String baseDir = "/home/hana/images/enc/hana/phone";
        Path baseDirPath = Path.of(baseDir);

        try{
            // 파일 방문자 구현을 사용하여 디렉토리 순회
            Files.walkFileTree(baseDirPath, new SimpleFileVisitor<Path>() {
//                int i = 0;
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // 파일 처리 로직
                    // 예: 파일 이름 출력
//                    if (i > 50) return FileVisitResult.TERMINATE;

                    if (Files.isDirectory(file)) {
                        log.info("Directory : {}", file.toAbsolutePath());
                    } else if (Files.isRegularFile(file)) {
                        log.info("File : {}", file.toAbsolutePath());
                        Path parentPath = file.getParent();
                        Path lastDirectoryName = parentPath.getFileName();
                        String accrNo = lastDirectoryName.toString();
                        log.info("AccrNo : {}", accrNo);
                        String fileName = file.getFileName().toString();

                        if (carrotFileRepository.findByAccrNoAndImageName(accrNo, fileName) != null) {
                            log.info("Already exist : {}", file.toAbsolutePath());
                            return FileVisitResult.CONTINUE;
                        }

                        List<OCRBox> boxes = doOCR(file.toAbsolutePath().toString());
                        List<ValueBox> valueBoxes = mergeAndSortOcrBoxes(boxes);
                        String labelString = makeLabelString(valueBoxes);
                        List<String> rows = mergeLabelsByRow(valueBoxes);

                        String docType = documentTypeChecker.checkPhoneDocType(valueBoxes, labelString);
                        log.info("DocType : {}", docType);
                        log.info(labelString);
                        CarrotFile carrotFile = CarrotFile.builder()
                                .accrNo(accrNo)
                                .imageName(file.getFileName().toString())
                                .docType(docType)
                                .labelString(labelString)
                                .build();
                        carrotFileRepository.save(carrotFile);

                        if (docType.equals("수리비명세서") || docType.equals("서비스내역서")) {
                            // Model 추출
                            String model = phoneExtractor.findModel(boxes, rows, labelString);
                            String manufacturer = "";
                            if (model.equals("NOT_FOUND")) {
                                manufacturer = "NOT_FOUND";
                            } else if (model.contains("iPhone")) {
                                manufacturer = "APPLE";
                            } else if (model.contains("SM")) {
                                manufacturer = "SAMSUNG";
                            } else if (model.contains("LM")) {
                                manufacturer = "LG";
                            }
                            // 수리비
                            SubPhone suri = phoneExtractor.findSuri(boxes, rows, labelString);
                            if (suri != null) {
                                Phone phone = Phone.builder()
                                        .accrNo(accrNo)
                                        .imageName(file.getFileName().toString())
                                        .modelCode(model)
                                        .manufacturer(manufacturer)
                                        .labelString(labelString)
                                        .build();
                                phone.update(suri);
                                phoneRepository.save(phone);
                            }

                            // 부품비
                            List<SubPhone> items = phoneExtractor.findItems(boxes, rows, labelString);
                            for (SubPhone item : items) {
                                Phone phone = Phone.builder()
                                        .accrNo(accrNo)
                                        .imageName(file.getFileName().toString())
                                        .modelCode(model)
                                        .manufacturer(manufacturer)
                                        .labelString(labelString)
                                        .build();
                                phone.update(item);
                                phoneRepository.save(phone);
                            }
                        }

//            DocType classifyResult = documentTypeChecker.getDocumentType(valueBoxes, importDto.getAccidentType());
//            aiPhoneRepairs = ruleOrganizer.runClaimRules(valueBoxes, rows, labelString, classifyResult);
////            visionResult = inputVerifier.verifyInput(importDto, imgFileInfoDto, aiDetails, classifyResult);
//            visionResult.setContent(makeRawString(valueBoxes));
                    } else {
                        log.info("ELSE : {}", file.toAbsolutePath());
                    }

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    // 파일 방문 실패 시 처리 로직
                    System.err.println(exc.getMessage());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }

//    public VisionResult doAutoInput(String autocaptureImage, ImportDto importDto, ImgFileInfoDto imgFileInfoDto) {
//
//        List<AiPhoneRepair> aiPhoneRepairs = new ArrayList<>();
//        VisionResult visionResult = VisionResult.createInitialResult();
//        try{
//            List<OCRBox> boxes = doOCR(autocaptureImage);
//            List<ValueBox> valueBoxes = mergeAndSortOcrBoxes(boxes);
//            String labelString = makeLabelString(valueBoxes);
//            List<String> rows = mergeLabelsByRow(valueBoxes);
//            DocType classifyResult = documentTypeChecker.getDocumentType(valueBoxes, importDto.getAccidentType());
//            aiPhoneRepairs = ruleOrganizer.runClaimRules(valueBoxes, rows, labelString, classifyResult);
////            visionResult = inputVerifier.verifyInput(importDto, imgFileInfoDto, aiDetails, classifyResult);
//            visionResult.setContent(makeRawString(valueBoxes));
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return visionResult;
//    }

    public List<OCRBox> doOCR(String imagePath) {
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
                    imagePath, formCsvPath, roiBox, bRemove, skewMode, formIdList, engine);

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

    public List<ValueBox> mergeAndSortOcrBoxes(List<OCRBox> boxes){

        if (boxes.size() == 0) return new ArrayList<>();

        List<ValueBox> valueBoxList = toValueBox(boxes);
//        List<BoxGroup> rows = valueBoxesToBoxGroup(valueBoxList, Constant.ROW, 1);

        List<BoxGroup> rows = makeRowsByCoords(valueBoxList);
        for (BoxGroup row: rows){
            row.mergeBoxesByCoord();
            Collections.sort(row.getValueBoxList(), new BoxSorter());
        }
        List<ValueBox> mergedBoxes = rows.stream()
                .flatMap(row -> row.getValueBoxList().stream()).collect(Collectors.toList());

        List<ValueBox> uniqueBox = mergedBoxes.stream().distinct().collect(Collectors.toList());

        return uniqueBox;
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

        if (boxList.size() == 0) return new ArrayList<>();
        ValueBox prevBox = boxList.get(0);
        String rowString = prevBox.getLabel();
        List<String> rowStrings = new ArrayList<>();

        for (int i = 1; i < boxList.size(); i ++){
            ValueBox box = boxList.get(i);
            prevBox = boxList.get(i - 1);

            // 박스가 이전박스보다 왼쪽에 있을 경우, 새로운 라인 시작
            // 숫자가 있는 경우는 컬럼 구분을 위해 식별자 | 앞에 추가 (수량, 단가 등이 가격에 영향)
            String boxLabel = box.getLabel();
            Pattern digit = Pattern.compile("\\d");
            Matcher digitMatcher = digit.matcher(boxLabel);
            if (digitMatcher.find()){
                boxLabel = "|" + boxLabel;
            }

            if (box.getRowId() > prevBox.getRowId()){
                rowStrings.add(rowString);
                rowString = boxLabel;
            }

            else if (box.getLeft() < prevBox.getLeft()){
                rowString = boxLabel + " " + rowString;
            } else{
                rowString += boxLabel + " ";
            }

            if (i == boxList.size() - 1){
                rowStrings.add(rowString);
            }
        }
        return rowStrings;
    }

    public class BoxSorter implements Comparator<ValueBox>{
        public int compare(ValueBox o1, ValueBox o2){
            if (Math.abs(o1.getTop()-o2.getTop()) <= 7 || Math.abs(o1.getBottom()-o2.getBottom()) <= 7){
                return o1.getLeft() - o2.getLeft();
            } return o1.getTop() - o2.getTop();
        }
    }
    // VisionTest-e

}
