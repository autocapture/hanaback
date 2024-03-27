package com.aimskr.ac2.hana.backend.vision.service;

import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.core.phone.domain.*;
import com.aimskr.ac2.hana.backend.core.phone.dto.PhoneAccdDto;
import com.aimskr.ac2.hana.backend.core.phone.service.PhoneRepairService;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class PhoneDetailAutoInputService {

    private final PhoneRepairDetailRepository phoneRepairDetailRepository;
    private final AiPhoneRepairDetailRepository aiPhoneRepairDetailRepository;
    private final PhoneRepairService phoneRepairService;
    private final CacheService cacheService;

    public void autoInputPhoneDetail(ImportDto importDto, ImgFileInfoDto imgFileInfoDto, List<ValueBox> boxList, List<String> rows, String labelString){

        String fileName = FileUtil.changeExtToJpg(imgFileInfoDto.getImgFileNm());
        List<String> identifier = cacheService.getDetailKeywords().get(ItemType.RPDT_ITEM_AMOUNT);
        List<PhoneAccd> accdList = cacheService.getPhoneAccd();

        ValueBox itemListIdentifier = boxList.stream()
                .filter(box -> identifier.stream().anyMatch(box.getLabel()::contains))
                .findFirst()
                .orElse(null);

        List<ValueBox> targetBoxList = boxList.stream()
                .filter(box -> (box.getRowId() >= itemListIdentifier.getRowId()))
                .collect(Collectors.toList());

        List<String> detailRows = rows.subList(itemListIdentifier.getRowId(), rows.size());
        for (String row: detailRows) {

            String detailCdRaw = "";
//            String detailAmount = rowValues.get(1);
            String detailPriceAmount = "";
            String detailTaxValue = "";

            List<String> rowValues = Arrays.asList(row.split("\\|"));

            if (rowValues.size() < 4) {
                continue;
            }
            if (row.contains("합계") && rowValues.size() == 3){
                detailCdRaw = rowValues.get(0);
                detailPriceAmount = rowValues.get(1);
                detailTaxValue = rowValues.get(2);
            } else{

                detailCdRaw = rowValues.get(0);
                detailPriceAmount = rowValues.get(2);
                detailTaxValue = rowValues.get(3);

            }



            detailPriceAmount = detailPriceAmount.replaceAll("[^0-9]", "");
            detailTaxValue = detailTaxValue.replaceAll("[^0-9]", "");


            PhoneAccdDto phoneAccdDto = phoneRepairService.getMatchedPhoneAccdDto(detailCdRaw);

            try{
                int detailPriceAmountInt = Integer.parseInt(detailPriceAmount);
                int detailTaxValueInt = Integer.parseInt(detailTaxValue);

                double ratio = (double) detailTaxValueInt / detailPriceAmountInt;

                // Round to one decimal point
                double roundedRatio = Math.round(ratio * 10) / 10.0;
                if (roundedRatio == 0.1 || roundedRatio == 0.0) {

                    AiPhoneRepairDetail aiPhoneRepairDetail = AiPhoneRepairDetail.builder()
                            .accrNo(importDto.getAcdNo())
                            .dmSeqno(importDto.getRctSeq())
                            .fileName(fileName)
                            .accdCd(phoneAccdDto.getAccdCd())
                            .accdNm(phoneAccdDto.getAccdNm())
                            .stdCd(phoneAccdDto.getStdCd())
                            .accdPriceAmt(detailPriceAmountInt)
                            .accdTaxAmt(detailTaxValueInt)
                            .accdAmt(detailPriceAmountInt + detailTaxValueInt)
                            .accdType(phoneAccdDto.getAccdType())
                            .accdTypeDtl(phoneAccdDto.getAccdTypeDtl())
                            .accdTypeNm(phoneAccdDto.getAccdTypeNm())
                            .build();

                    aiPhoneRepairDetailRepository.save(aiPhoneRepairDetail);

                    log.debug("phoneRepairDetail: {}", aiPhoneRepairDetail.toString());
                    phoneRepairDetailRepository.save(aiPhoneRepairDetail.toEntity());
                }

            }catch(NumberFormatException e){
                log.error("detailPriceAmountInt or detailTaxValueInt is not a number");
            }
        }
    }


}
