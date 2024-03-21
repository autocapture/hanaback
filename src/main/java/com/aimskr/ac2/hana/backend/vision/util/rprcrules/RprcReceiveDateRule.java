package com.aimskr.ac2.hana.backend.vision.util.rprcrules;

import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.ClaimRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RprcReceiveDateRule extends ClaimRule {

    private final CacheService cacheService;

    String [] patterns = {"20\\d\\d(-|/|[.])(0|1)[0-9](-|/|[.])[0-3][0-9]",
            "20\\d\\d년(0|1)?[0-9]월[0-3]?[0-9]일",
            "20\\d\\d(-|/|[.]|,)?(0|1)?[0-9](-|/|[.]|,)?[0-3]?[0-9]",
            "(0|1)?[0-9](-|/|[.])?[0-3]?[0-9](-|/|[.])?2\\d"
    };

    @Override
    public void setItemInfo(DocType docType){
        initItem(ItemType.RPRC_RECEIVE_DATE);
    }

    @Override
    public void applyRule(List<ValueBox> boxList, List<String> rows, String labelString) {

        String receiveDate = "";
        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.RPRC_RECEIVE_DATE);

        ValueBox identifierBox = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        if (identifierBox != null) {
            int expRow = identifierBox.getRowId();

            List<ValueBox> targetBoxes = boxList.stream()
                    .filter(box -> box.getRowId() == expRow)
                    .collect(Collectors.toList());

            if (targetBoxes.size() == 0) return;
            int tBoxIdx = 0;


            for (ValueBox tBox : targetBoxes) {

                ValueBox targetBox = targetBoxes.get(tBoxIdx);

                String tBoxValue = tBox.getLabel();
                String tBoxDigit = tBoxValue.replaceAll("[^0-9]", "");
                while (tBoxDigit.length() > 0 && tBoxDigit.length() <= 6 && tBoxIdx + 1 < targetBoxes.size()) {
                    tBox.join(targetBoxes.get(tBoxIdx + 1));
                    tBoxIdx++;
                }

                log.debug("[applyRule] tBoxValue {}", tBox.getLabel());
                for (String pattern : patterns) {

                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(tBox.getLabel());
                    log.debug("Pattern: {}", pattern);
                    if (m.find()) {
                        String expDateRaw = m.group();
                        expDateRaw = expDateRaw.replace(".", "-").replace("/", "-").replace("년", "-").replace("월", "-").replace("일", "");
//                    expDateRaw = expDateRaw.replaceAll("[^0-9]", "");
                        log.debug("[applyRule] expDateRaw {}", expDateRaw);
                        String[] date = expDateRaw.split("-");

                        if (date.length != 3) continue;

                        String yearCandid = date[0];
                        String monthCandid = date[1];
                        String dayCandid = date[2];

                        // 양쪽 끝이 4자리(연도)로 끝나지 않는 경우,
                        if (yearCandid.length() < 4 && dayCandid.length() < 4) {
                            try {
                                if (Integer.parseInt(date[0]) < 12 && Integer.parseInt(date[2]) >= 20) {
                                    monthCandid = date[0];
                                    dayCandid = date[1];
                                    yearCandid = date[2];
                                }
                            } catch (Exception ParseException) {
                                log.debug("[applyRule] date parsing error");
                                continue;
                            }
                        }

                        if (monthCandid.length() == 1) {
                            monthCandid = "0" + monthCandid;
                        }
                        if (dayCandid.length() == 1) {
                            dayCandid = "0" + dayCandid;
                        }

                        if (yearCandid.length() == 2) {
                            yearCandid = "20" + yearCandid;
                        }

                        expDateRaw = yearCandid + monthCandid + dayCandid;
//                        receiveDate = timeFormatter(expDateRaw);
                        receiveDate = expDateRaw;
                        log.debug("[applyRule] expDate {}", receiveDate);

                        break;
                    }
                }


            }
            setItemValue(receiveDate);
            setAccuracy(getAccuracy(boxList, receiveDate));

        }
    }

    @Override
    public void calcAccuracy(){


    }

}
