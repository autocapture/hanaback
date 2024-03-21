package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.channel.json.ImportDto;
import com.aimskr.ac2.hana.backend.core.phone.domain.AiPhoneRepair;
import com.aimskr.ac2.hana.backend.core.phone.domain.AiPhoneRepairRepository;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetail;
import com.aimskr.ac2.hana.backend.core.phone.domain.PhoneRepairDetailRepository;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.VisionResult;
import com.aimskr.ac2.hana.backend.vision.service.PhoneDetailAutoInputService;
import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.common.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
//
@Slf4j
@RequiredArgsConstructor
@Service
public class InputVerifier {
    private final AiPhoneRepairRepository aiPhoneRepairRepository;
    private final PhoneRepairDetailRepository phoneRepairDetailRepository;
    private final CacheService cacheService;

    @Transactional
    public VisionResult verifyInput(ImportDto importDto, ImgFileInfoDto imgFileInfoDto, List<AiPhoneRepair> aiPhoneRepairs, DocType docType){

        String accrNo = importDto.getAccrNo();
        String dmSeqno = importDto.getDmSeqno();
        String fileName = FileUtil.changeExtToJpg(imgFileInfoDto.getFileNm());

        List<AiPhoneRepair> aiPhoneRepairPrevs = aiPhoneRepairRepository.findByKeyAndFileName(accrNo, dmSeqno, fileName);

        // 기존 AI Detail 삭제
        if (aiPhoneRepairPrevs.size() > 0){
            for (AiPhoneRepair aiPhoneRepairPrev : aiPhoneRepairPrevs){
                aiPhoneRepairRepository.delete(aiPhoneRepairPrev);
            }
        }

        if (docType.equals(DocType.ETCS)){
            return VisionResult.builder()
                    .isError(false)
                    .isInputRequired(false)
                    .isQa(false)
                    .docType(docType)
                    .content("")
                    .build();
        }

        int itemAmount = calcItemAmount(accrNo, dmSeqno, fileName);
        int rprAmount = 0;
        try{
            AiPhoneRepair totalAmountDetail = aiPhoneRepairs.stream().filter(aiPhoneRepair -> aiPhoneRepair.getItemCode().equals(ItemType.RPDT_TOTAL_AMOUNT.getItemCode())).findFirst().orElse(null);
            if (totalAmountDetail != null){
                String totalAmount = totalAmountDetail.getItemValue();
                int totalAmountInt = Integer.parseInt(totalAmount);

                if (itemAmount > 0 ){
                    rprAmount = totalAmountInt - itemAmount;
                }
            }

        } catch(Exception e){
            log.error("[InputVerifier] - verifyInput, Calculating RPR Amount, error: {}", e);
        }

        //QA Rule
        for (AiPhoneRepair aiPhoneRepair : aiPhoneRepairs) {
            aiPhoneRepair.updateKey(accrNo, dmSeqno, fileName);

            if (aiPhoneRepair.getItemName().equals(ItemType.RPDT_ITEM_AMOUNT.getItemName())){
                aiPhoneRepair.updateValue(String.valueOf(itemAmount));
            }

            if (aiPhoneRepair.getItemName().equals(ItemType.RPDT_RPR_AMOUNT.getItemName())){
                aiPhoneRepair.updateValue(String.valueOf(rprAmount));
            }

            if (aiPhoneRepair.getItemName().length() > 254){
                aiPhoneRepair.updateValue("");
            }

            aiPhoneRepairRepository.save(aiPhoneRepair);
            log.debug("[InputVerifier] - inputVerified, aiDetail: {}", aiPhoneRepair);

        }

        // Validation(QA) Rule
        return VisionResult.builder()
                .isError(false)
                .isInputRequired(true)
                .isQa(false)
                .docType(docType)
                .content("")
                .build();
    }

    public int calcItemAmount(String accrNo, String dmSeqno, String fileName){

        try{

            List<PhoneRepairDetail> phoneRepairDetails = phoneRepairDetailRepository.findByKeyAndFileName(accrNo, dmSeqno, fileName);
            if (phoneRepairDetails.size() > 0){

                return phoneRepairDetails.stream().mapToInt(PhoneRepairDetail::getAccdAmt).sum();

            }
        } catch(Exception e){
            log.error("[InputVerifier] - verifyInput, Calculating Item Amount, error: {}", e);
        }

        return 0;

    }



}
