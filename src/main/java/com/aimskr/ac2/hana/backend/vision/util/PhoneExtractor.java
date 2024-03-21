package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.hana.backend.phone_old.domain.SubPhone;
import com.synap.ocr.sdk.OCRBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class PhoneExtractor {

    // 수리비
    public SubPhone findSuri(List<OCRBox> boxes, List<String> rows, String labelString) {
        // 수리비 객체 생성
        SubPhone subPhone = null;

        for (String row : rows) {
            if (!row.contains("수리비명세서") && row.contains("수리비")) {
                subPhone = new SubPhone();
                subPhone.setDivision("공임비");
                subPhone.setItemCode("");

                String regex = "\\b(?:\\d{1,3}(?:[.,]\\d{3})?|\\d{1,2})\\b";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(row);
                String supplyPrice = "NOT_FOUND";
                if (matcher.find()) {
                    supplyPrice = matcher.group();
                    subPhone.setSupplyPrice(supplyPrice);
                }
                if (matcher.find()) {
                    subPhone.setTax(matcher.group());
                }

                int end = row.indexOf(supplyPrice);
                subPhone.setItemName(row.substring(0, end > 0 ? end : row.length()));
                break;
            }
        }
        return subPhone;
    }

    // 수리비
    public List<SubPhone> findItems(List<OCRBox> boxes, List<String> rows, String labelString) {
        List<SubPhone> items = new ArrayList<>();

        for (String row : rows) {
            // GH82-24597A
            Pattern ghPattern = Pattern.compile("GH[A-Za-z0-9]{2}-[A-Za-z0-9]{6}");
            Matcher ghMatcher = ghPattern.matcher(row);

            if (ghMatcher.find()) {
                SubPhone subPhone = new SubPhone();
                subPhone.setDivision("부품비");
                String ghCode = ghMatcher.group();
                subPhone.setItemCode(ghCode);

                String regex = "\\b(?:\\d{1,3}(?:[.,]\\d{3})?|\\d{1,2})\\b";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(row);
                String supplyPrice = "NOT_FOUND";
                if (matcher.find()) {
                    supplyPrice = matcher.group();
                    subPhone.setSupplyPrice(supplyPrice);
                }
                if (matcher.find()) {
                    subPhone.setTax(matcher.group());
                }
                int start = row.indexOf(ghCode) + ghCode.length();
                int end = row.indexOf(supplyPrice);
                if (start > end ) end = row.length();
                subPhone.setItemName(row.substring(start, end));
                items.add(subPhone);
            }

            // 661-22309
            Pattern iphonePattern = Pattern.compile("[0-9]{3}-[0-9]{5}");
            Matcher iphoneMatcher = iphonePattern.matcher(row);

            if (iphoneMatcher.find()) {
                SubPhone subPhone = new SubPhone();
                subPhone.setDivision("부품비");
                String iphoneCode = iphoneMatcher.group();
                subPhone.setItemCode(iphoneCode);

                String regex = "\\b(?:\\d{1,3}(?:[.,]\\d{3})?|\\d{1,2})\\b";

                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(row);
                String amount = "NOT_FOUND";
                if (matcher.find()) {
                    amount = matcher.group();
                    subPhone.setAmount(amount);
                }
                int start = row.indexOf(iphoneCode) + iphoneCode.length();
                int end = row.indexOf(amount);
                if (start > end ) end = row.length();
                subPhone.setItemName(row.substring(start, end));
                items.add(subPhone);
            }


        }
        return items;
    }

    // 모델명 추출
    public String findModel(List<OCRBox> boxes,List<String> rows, String labelString) {
        // 패턴: "SM-"로 시작하고 4~5개의 문자 뒤에 공백이 오는 경우
        String regex = "SM-[A-Za-z0-9]{4,5}";
        // Pattern 객체 생성
        Pattern pattern = Pattern.compile(regex);
        // Matcher 객체 생성
        Matcher matcher = pattern.matcher(labelString);
        // Matcher를 사용하여 문자열에서 패턴 찾기
        while (matcher.find()) {
            // 매칭된 패턴 추출
            String match = matcher.group().trim(); // 공백 제거
            return match;
        }

        // 패턴: "LM-"로 시작하고 4~5개의 문자 뒤에 공백이 오는 경우
        String regex2 = "LM-[A-Za-z0-9]{4,5}";
        // Pattern 객체 생성
        Pattern pattern2 = Pattern.compile(regex2);
        // Matcher 객체 생성
        Matcher matcher2 = pattern2.matcher(labelString);
        // Matcher를 사용하여 문자열에서 패턴 찾기
        while (matcher2.find()) {
            // 매칭된 패턴 추출
            String match = matcher2.group().trim(); // 공백 제거
            return match;
        }

        // iPhone
        for (String row : rows) {
            if (row.contains("iPhone")) {
                return row;
            }
        }

        return "NOT_FOUND";
    }
}
