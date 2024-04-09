package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.hana.backend.core.phone.domain.AiPhoneRepair;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.rpsvrules.RpsvOtherInsRule;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.vision.util.rpdtrules.*;
import com.aimskr.ac2.hana.backend.vision.util.rprcrules.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RuleOrganizer {

    //수리비명세서
    @Autowired
    private RpdtIssueDateRule rpdtIssueDateRule;
    @Autowired
    private RpdtImeiRule rpdtImeiRule;
    @Autowired
    private RpdtItemAmountRule rpdtItemAmountRule;
    @Autowired
    private RpdtManuNumRule rpdtManuNumRule;
    @Autowired
    private RpdtModelCodeRule rpdtModelCodeRule;
    @Autowired
    private RpdtRprAmountRule rpdtRprAmountRule;
    @Autowired
    private RpdtSerialNumRule rpdtSerialNumRule;
    @Autowired
    private RpdtTotalAmountRule rpdtTotalAmountRule;

    // 수리비영수증
    @Autowired
    private RprcIssueDateRule rprcIssueDateRule;
    @Autowired
    private RprcReceiveDateRule rprcReceiveDateRule;
    @Autowired
    private RprcTotalAmountRule rprcTotalAmountRule;

    // 부가서비스내역서
    @Autowired
    private RpsvOtherInsRule rpsvOtherInsRule;



    private HashMap<DocType, List<ClaimRule>> claimRules;
    private HashMap<DocType, List<SubscriptionRule>> subscriptionRules;


    @PostConstruct
    public void init(){
        claimRules = new HashMap<>();
        subscriptionRules = new HashMap<>();
//        // 01. 의료비영수증-입원
//        claimRules.put(DocType.MDRI, new ArrayList<>());
//        // 02. 의료비영수증-통원
//        claimRules.put(DocType.MDRO, new ArrayList<>());
//        // 03. 약국영수증
//        claimRules.put(DocType.MDRC, new ArrayList<>());
//        // 12. 수리비명세서
//        claimRules.put(DocType.RPDT, new ArrayList<>());
//        claimRules.get(DocType.RPDT).add(rpdtIssueDateRule);
//        claimRules.get(DocType.RPDT).add(rpdtImeiRule);
//        claimRules.get(DocType.RPDT).add(rpdtItemAmountRule);
//        claimRules.get(DocType.RPDT).add(rpdtManuNumRule);
//        claimRules.get(DocType.RPDT).add(rpdtModelCodeRule);
//        claimRules.get(DocType.RPDT).add(rpdtRprAmountRule);
//        claimRules.get(DocType.RPDT).add(rpdtSerialNumRule);
//        claimRules.get(DocType.RPDT).add(rpdtTotalAmountRule);
//
//
//        // 13. 수리비영수증
//        claimRules.put(DocType.RPRC, new ArrayList<>());
//        claimRules.get(DocType.RPRC).add(rprcIssueDateRule);
//        claimRules.get(DocType.RPRC).add(rprcReceiveDateRule);
//        claimRules.get(DocType.RPRC).add(rprcTotalAmountRule);
//
//        // 14. 부가서비스내여서
//        claimRules.put(DocType.RPSV, new ArrayList<>());
//        claimRules.get(DocType.RPSV).add(rpsvOtherInsRule);
//
//        // 98. 휴대폰청약서류
//        subscriptionRules.put(DocType.PHIF, new ArrayList<>());


        // 99.기타
        claimRules.put(DocType.ETCS, new ArrayList<>());
    }

    public List<AiPhoneRepair> runClaimRules(List<ValueBox> boxList, List<String> rows, String labelString, DocType docType){
        log.debug("[RuleOrganizer] runRules() - docType: {}/{}", docType.getDocCode(), docType.getDocName());
        log.debug("[RuleOrganizer] runRules() - rule.size() : {}", claimRules.size());

        List<ClaimRule> typedRules = claimRules.get(docType);
        List<AiPhoneRepair> aiPhoneRepairs = new ArrayList<>();

        if (typedRules == null){
            log.debug("[RuleOrganizer] runRules() - typedRules is null");
            return aiPhoneRepairs;
        } else {
            log.debug("[RuleOrganizer] runRules() - typedRules.size() : {}", typedRules.size());
        }

        for (ClaimRule rule: typedRules){
            rule.setItemInfo(docType);
            try{
                rule.applyRule(boxList, rows, labelString);
                rule.calcAccuracy();
            } catch (Exception e){
                log.error("[runRules] {} Rule error : {}", rule.getItemName(), e.getMessage());
            }

            AiPhoneRepair aiPhoneRepair = rule.makeAiDetail();
            aiPhoneRepairs.add(aiPhoneRepair);
        }
        return aiPhoneRepairs;
    }



}
