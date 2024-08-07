package com.aimskr.ac2.hana.backend.vision.util;


import com.aimskr.ac2.common.enums.doc.AccidentType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.domain.DocKeyword;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentTypeChecker { // 문서 서식 판별기

    private final CacheService cacheService;

    public long findWordFrequency(List<DocKeyword> docKeywords,
                                  String labelString,
                                  int confidence){

        long frequency = 0;
        for (DocKeyword docKeyword: docKeywords){
            if (labelString.indexOf(docKeyword.getWord()) > -1 && docKeyword.getConfidence() == confidence){
                frequency += 1;
                log.debug("[DocumentTypeChecker] findWordFrequency - word: {}, frequency: {}",
                        docKeyword.getWord(), frequency);
            }
        }
        return frequency;
    }

    // 자동차보험금지급내역서 인지 확인
//    public boolean isCarClaimDocType(List<ValueBox> boxList, String labelString){
//        labelString = labelString.toLowerCase().replace(" ", "");
//        if (labelString.indexOf("지급내역") > -1
//                || labelString.indexOf("지급결의") > -1
//                || labelString.indexOf("상해급수") > -1
//                || labelString.indexOf("보상처리") > -1
//                || labelString.indexOf("공제처리") > -1
//                || labelString.indexOf("상해등급") > -1
//                || (labelString.indexOf("사고사실") > -1 && labelString.indexOf("확인서") > -1)
//        ){
//            return true;
//
//        } else {
//            return false;
//        }
//    }

    public DocType getDocTypeFromFrequencyWordMap(Map<String, Long> mapByFrequency, String labelString){

        Comparator<Entry<String, Long>> comparator = new Comparator<Entry<String, Long>>() {
            @Override
            public int compare(Entry<String, Long> e1, Entry<String, Long> e2) {
                return e1.getValue().compareTo(e2.getValue());
            }
        };

        Entry<String, Long> maxEntry = Collections.max(mapByFrequency.entrySet(), comparator);
        String confidentWord = maxEntry.getKey();
        log.debug("[getDocTypeFromFrequencyWordMap] confidentWord: {},", confidentWord);

        DocType docTypeFinal = DocType.typeEquals(confidentWord);

        // 최대 출현 수가 완전하게 맞지 않는다면
        if (docTypeFinal.equals(DocType.ETCS)){
            docTypeFinal = DocType.typeContains(confidentWord);
        }

        log.info("[getDocTypeFromFrequencyWordMap] DocType: {},", docTypeFinal.toString());


        return docTypeFinal;
    }


    // 문서 분류기
    public DocType getDocumentType(List<ValueBox> boxList, String labelString){

        DocType docType = DocType.ETCS;

//        AccidentType accType = AccidentType.getEnum(accidentCd);

//        if (accType == null) {
//            return DocType.ETCS;
//        }

        if (boxList.size() < 5){
            return DocType.ETCS;
        }

        List<DocKeyword> dkListAll = cacheService.getDocKeywords();
        List<DocKeyword> dkwListByAcType = dkListAll.stream()
                .collect(Collectors.toList());
        Map<String, List<DocKeyword>> categoryDkwMap = dkwListByAcType.stream()
                .collect(Collectors.groupingBy(DocKeyword::getWordCategory));

        // 1줄로 만든걸 공백없애고 소문자로 일괄 변환
        labelString = labelString.toLowerCase().replace(" ", "");

        Map<String, Long> wordFrequencyMapConfLvl2 = new HashMap<>();
        Map<String, Long> wordFrequencyMapConfLvl1 = new HashMap<>();

        // calculate frequency of words by confidence level
        for (Entry<String, List<DocKeyword>> entry: categoryDkwMap.entrySet()){
            List<DocKeyword> dkws = entry.getValue();
            String kw = entry.getKey();
            long frequencyConf2 = findWordFrequency(dkws, labelString, 2);
            long frequencyConf1 = findWordFrequency(dkws, labelString, 1);
            if (frequencyConf2 > 0){
                log.debug("[DocumentTypeChecker] mappedDocumentType - kw: {}, frequencyConf2: {}", kw, frequencyConf2);

                wordFrequencyMapConfLvl2.put(kw, frequencyConf2);
            }
            if (frequencyConf1 > 0){
                log.debug("[DocumentTypeChecker] mappedDocumentType - kw: {}, frequencyConf1: {}", kw, frequencyConf1);
                wordFrequencyMapConfLvl1.put(kw, frequencyConf1);
            }
        }

        if (wordFrequencyMapConfLvl2.size() > 0){
            docType = getDocTypeFromFrequencyWordMap(wordFrequencyMapConfLvl2, labelString);
        }

        if (wordFrequencyMapConfLvl2.size() == 0 && wordFrequencyMapConfLvl1.size() > 0) {
            docType =  getDocTypeFromFrequencyWordMap(wordFrequencyMapConfLvl1, labelString);
        }

        int wordFrequencyMapSize = wordFrequencyMapConfLvl2.size() + wordFrequencyMapConfLvl1.size();

//        return checkFinalDocTypeByShapeAndKeyword(boxList, labelString, docType, wordFrequencyMapSize);
        return docType;

    }

}
