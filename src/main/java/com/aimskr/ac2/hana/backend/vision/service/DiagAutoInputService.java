package com.aimskr.ac2.hana.backend.vision.service;


import com.aimskr.ac2.common.enums.detail.ItemType;
import com.aimskr.ac2.common.util.FileUtil;
import com.aimskr.ac2.hana.backend.channel.json.ImgFileInfoDto;
import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfo;
import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfoRepository;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.KcdResponseDto;
import com.aimskr.ac2.hana.backend.core.medical.service.DiagInfoService;
import com.aimskr.ac2.hana.backend.util.service.CacheService;
import com.aimskr.ac2.hana.backend.vision.dto.ValueBox;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class DiagAutoInputService {

    private final DiagInfoRepository diagInfoRepository;
    private final DiagInfoService diagInfoService;
    private final CacheService cacheService;


    @Transactional
    public void autoInputDiags(String rqsReqId, String accrNo, String dmSeqno, ImgFileInfoDto imgFileInfoDto, List<String> rows, String labelString){

        String fileName = FileUtil.changeExtToJpg(imgFileInfoDto.getImgFileNm());

        List<DiagInfoExchangeDto> diagInfos = doAutoInputDiags(rows, labelString);
        for (DiagInfoExchangeDto diagInfo : diagInfos) {

            DiagInfo diag = DiagInfo.of(diagInfo);
            diag.updateKey(rqsReqId, accrNo, dmSeqno, fileName);

            diagInfoRepository.save(diag);
        }
    }

    public List<DiagInfoExchangeDto> doAutoInputDiags(List<String> rows, String labelString)  {
        List<DiagInfoExchangeDto> diagInfos = new ArrayList<>();

        String diagStage = findDiagStage(rows);

        for (String diagRow : rows) {
            List<DiagInfoExchangeDto> diags = findDiagsFromText(diagRow);
            diagInfos.addAll(diags);
        }

        if (diagInfos.size() == 0) {
            List<DiagInfoExchangeDto> diags = findDiagsFromText(labelString);
            diagInfos.addAll(diags);
        }

        for (DiagInfoExchangeDto diagInfoExchangeDto: diagInfos){
            diagInfoExchangeDto.setDiagStage(diagStage);
        }

        return  diagInfos;
    }

    public String findDiagStage(List<String> rows) {

        for (String row: rows){
            int idx = row.indexOf("임상");
            if (idx > 1){
                String checkBox = row.substring(0, idx);
                if (checkBox.contains("ㄹ") || checkBox.toLowerCase().contains("v")){
                    return "임상";
                }
            }
        }
        return "최종";
    }

    public List<DiagInfoExchangeDto> findDiagsFromText(String labels) {
        List<DiagInfoExchangeDto> diagInfos = new ArrayList<>();

        Pattern diagPattern = Pattern.compile("[A-U][0-9Oo]{2}[0-9Oo.]{0,3}(?!\\*+|g|m)");

        Matcher diagMatcher = diagPattern.matcher(labels);

        String mnDgYn = "주진단";
        String diagStage = "최종";
        int count = 0;

        while (diagMatcher.find()) {
            String diagCodeRaw = diagMatcher.group();
            // 특정 진단서 양식 M94** 이런식으로 나오는 경우 제외
            String nums = diagCodeRaw.substring(1).replace("O", "0").replace("o", "0");

            String diagCode = diagCodeRaw.substring(0, 1) + nums;

            log.debug("diagCode : {}", diagCode);
            KcdResponseDto kcd = diagInfoService.findKcdName(diagCode);
            log.debug("kcd:" + kcd);
            if (kcd != null) {

                if (count > 0){
                    mnDgYn = "부진단";
                }

                DiagInfoExchangeDto diagInfo = DiagInfoExchangeDto.builder()
                        .dsacd(kcd.getKcdCd())
                        .mnDgnYn(mnDgYn)
                        .diagStage(diagStage)
                        .dsnm(kcd.getKcdName())
                        .build();

                if (!diagInfos.contains(diagInfo)){
                    diagInfos.add(diagInfo);
                    count ++;
                }
            }
        }

        return diagInfos;
    }
}
