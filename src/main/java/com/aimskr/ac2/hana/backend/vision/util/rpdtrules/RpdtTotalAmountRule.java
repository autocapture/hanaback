package com.aimskr.ac2.hana.backend.vision.util.rpdtrules;

import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Component
@Slf4j
public class RpdtTotalAmountRule extends ClaimRule {

    private final CacheService cacheService;
    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPDT_TOTAL_AMOUNT);
    }


    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString){

        String totalAmount = "";
        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.RPDT_TOTAL_AMOUNT);
        log.debug("Rows: {}", rows);

        List<String> matchedElements = rows.stream()
                .filter(element -> identifier.stream().anyMatch(element::contains) && !element.contains("니다"))
                .collect(Collectors.toList());

        // 금액을 지칭하는 단어가 확인되지 않을 경우, 전체 스트링에서 금액 형식 추출
        if (matchedElements.size() == 0){
            matchedElements.add(String.join("|", rows));
        }
        log.debug("matchedElements: {}", matchedElements);

        totalAmount = getAmountValue(matchedElements);

        if (StringUtils.hasText(totalAmount)){
            try{
                int totalAmountNum = Integer.parseInt(totalAmount);

                totalAmount = String.valueOf(totalAmountNum);
            } catch(NumberFormatException e){
                log.error("[TotalAmountRule] NumberFormatException: {}", e.getMessage());
            }
        }
//        }

        setItemValue(totalAmount);
        setAccuracy(getAccuracy(boxList, totalAmount));

    }

    public String getAmountValue(List<String> matchedElements){

        String totalAmount = "";
        int i = 0;
        while (i < matchedElements.size()){

            String amountCandid = extractDigitValue(matchedElements.get(i));
            amountCandid = amountCandid.replaceAll("\\.(?!\\.)", "");
            if (getItemCode().equals("M0140007")){
                amountCandid = amountCandid.replace("-", "");
            }
            // 1000원 이상, 10만원 미만일 때 통과
            if (!StringUtils.hasText(totalAmount) && amountCandid.length() < 7 && amountCandid.length() > 3){
                totalAmount = amountCandid;
            } else if (StringUtils.hasText(amountCandid)){
                try{
                    int amountCandidNum = Integer.parseInt(amountCandid);
                    int totalAmountNum = Integer.parseInt(totalAmount);
                    if (amountCandidNum < 100000 && totalAmountNum < amountCandidNum){
                        totalAmount = amountCandid;
                    }
                } catch(NumberFormatException e){
                    log.error("[TotalAmountRule] getAmountValue-NumberFormatException: {}", e.getMessage());
                    i ++;
                    continue;
                }
            }
            i ++;
        }

        return totalAmount;

    }

    public String extractDigitValue(String rowString) {

        if (rowString.contains("잔액")){
            return "";
        }

        log.debug("ExtractDigitValue: {}", rowString);

        String[] elementsInRow = rowString.split("/|원|\\|");
        List<String> digitValues = new ArrayList<>();
        for (String element : elementsInRow) {
            Pattern digit = Pattern.compile("-?\\d");
            Matcher digitMatcher = digit.matcher(element);

            Pattern digitO = Pattern.compile("-?[0-9O]+");
            Matcher digitOMatcher = digitO.matcher(element);
            if (digitOMatcher.find()){
                element = element.replace("O", "0");
            }

            if (digitMatcher.find()) {
                String digitValue = element.replaceAll("[^a-zA-Z0-9...]", "");
                if (digitMatcher.group().indexOf("-") == 0) {
                    digitValue = "-" + digitValue;
                }
                if (digitValue.length() < 7){
                    digitValues.add(digitValue);
                }
            }
        }
        // 길이가 가장 긴 숫자 return
        // 끝이 00인 숫자를 먼저 찾는다.
        log.debug("digitValues: {}", digitValues);
        Optional<String> digitValueString = digitValues.stream()
                .filter(v -> v.matches(".*00$")).max(Comparator.comparingInt(String::length));

        if (digitValueString.isPresent()) {

            String digitValue = digitValueString.get();

            if (digitValue.length() > 3) {
                return digitValue;
            } else {

                // 1000원 이하 건
                int index = digitValues.indexOf(digitValue);
                if (index >= 1) {
                    String newDigit = digitValues.get(index - 1) + digitValue;
                    if (newDigit.length() < 7){
                        return digitValues.get(index - 1) + digitValue;
                    } else {
                        return "";
                    }
                } else {
                    return digitValueString.get();
                }
            }

        } else {
            Optional<String> digitValueString2 = digitValues.stream()
                    .max(Comparator.comparingInt(String::length));

            if (digitValueString2.isPresent() && digitValueString2.get().length() > 3 && digitValueString2.get().length() < 7) {
                return digitValueString2.get();
            }else{
                return "";
            }
        }
    }

    @Override
    public void calcAccuracy(){
        // 검증로직
    }
}
