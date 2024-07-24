package com.aimskr.ac2.hana.backend.vision.util;

import com.aimskr.ac2.hana.backend.vision.domain.AiDetail;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import com.aimskr.ac2.hana.backend.vision.util.cipsrules.CipsInjGrdRule;
import com.aimskr.ac2.hana.backend.vision.util.cipsrules.CipsInsComRule;
import com.aimskr.ac2.hana.backend.vision.util.cipsrules.CipsProcessTypeRule;
import com.aimskr.ac2.hana.backend.vision.util.mddgrules.MddgBizNameRule;
import com.aimskr.ac2.common.enums.doc.DocType;
import com.aimskr.ac2.hana.backend.vision.util.mddgrules.*;
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

    // 진단,수술
    // 의료기관명
    @Autowired
    private MddgBizNameRule mddgBizNameRule;
    // 진단일
    @Autowired
    private MddgDiagDateRule mddgDiagDateRule;
    // 의사명
    @Autowired
    private MddgDoctorNameRule mddgDoctorNameRule;
    // 면허번호
    @Autowired
    private MddgLicenseNoRule mddgLicenseNoRule;

    @Autowired
    private CipsInsComRule cipsInsComRule;

    @Autowired
    private CipsInjGrdRule cipsInjGrdRule;

    @Autowired
    private CipsProcessTypeRule cipsProcessTypeRule;


    private HashMap<DocType, List<ClaimRule>> claimRules;


    @PostConstruct
    public void init(){
        claimRules = new HashMap<>();
//        // 01. 의료비영수증-입원
        claimRules.put(DocType.MDDG, new ArrayList<>());
        claimRules.get(DocType.MDDG).add(mddgBizNameRule);
        claimRules.get(DocType.MDDG).add(mddgDiagDateRule);
        claimRules.get(DocType.MDDG).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDDG).add(mddgLicenseNoRule);

        claimRules.put(DocType.MDOP, new ArrayList<>());
        claimRules.get(DocType.MDOP).add(mddgBizNameRule);
        claimRules.get(DocType.MDOP).add(mddgDiagDateRule);
        claimRules.get(DocType.MDOP).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDOP).add(mddgLicenseNoRule);


        claimRules.put(DocType.MDSR, new ArrayList<>());
        claimRules.get(DocType.MDSR).add(mddgBizNameRule);
        claimRules.get(DocType.MDSR).add(mddgDiagDateRule);
        claimRules.get(DocType.MDSR).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDSR).add(mddgLicenseNoRule);

        claimRules.put(DocType.MDIC, new ArrayList<>());
        claimRules.get(DocType.MDIC).add(mddgBizNameRule);
        claimRules.get(DocType.MDIC).add(mddgDiagDateRule);
        claimRules.get(DocType.MDIC).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDIC).add(mddgLicenseNoRule);

        claimRules.put(DocType.MDOC, new ArrayList<>());
        claimRules.get(DocType.MDOC).add(mddgBizNameRule);
        claimRules.get(DocType.MDOC).add(mddgDiagDateRule);
        claimRules.get(DocType.MDOC).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDOC).add(mddgLicenseNoRule);

        claimRules.put(DocType.MDPH, new ArrayList<>());
        claimRules.get(DocType.MDPH).add(mddgBizNameRule);
        claimRules.get(DocType.MDPH).add(mddgDiagDateRule);
        claimRules.get(DocType.MDPH).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDPH).add(mddgLicenseNoRule);

        claimRules.put(DocType.MDEC, new ArrayList<>());
        claimRules.get(DocType.MDEC).add(mddgBizNameRule);
        claimRules.get(DocType.MDEC).add(mddgDiagDateRule);
        claimRules.get(DocType.MDEC).add(mddgDoctorNameRule);
        claimRules.get(DocType.MDEC).add(mddgLicenseNoRule);

        claimRules.put(DocType.SRRC, new ArrayList<>());
        claimRules.get(DocType.SRRC).add(mddgBizNameRule);
        claimRules.get(DocType.SRRC).add(mddgDiagDateRule);
        claimRules.get(DocType.SRRC).add(mddgDoctorNameRule);
        claimRules.get(DocType.SRRC).add(mddgLicenseNoRule);

        claimRules.put(DocType.CIPS, new ArrayList<>());
        claimRules.get(DocType.CIPS).add(cipsInsComRule);
        claimRules.get(DocType.CIPS).add(cipsInjGrdRule);
        claimRules.get(DocType.CIPS).add(cipsProcessTypeRule);

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


    public List<AiDetail> runClaimRules(List<ValueBox> boxList, List<String> rows, String labelString, DocType docType){
        log.debug("[RuleOrganizer] runRules() - docType: {}/{}", docType.getDocCode(), docType.getDocName());
        log.debug("[RuleOrganizer] runRules() - rule.size() : {}", claimRules.size());

        List<ClaimRule> typedRules = claimRules.get(docType);
        List<AiDetail> aiDetails = new ArrayList<>();

        if (typedRules == null){
            log.debug("[RuleOrganizer] runRules() - typedRules is null");
            return aiDetails;
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

            AiDetail aiDetail = rule.makeAiDetail();
            aiDetails.add(aiDetail);
        }
        return aiDetails;
    }



}
