package com.aimskr.ac2.hana.backend.core.medical.service;


import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfo;
import com.aimskr.ac2.hana.backend.core.medical.domain.DiagInfoRepository;
import com.aimskr.ac2.hana.backend.core.medical.domain.Kcd;
import com.aimskr.ac2.hana.backend.core.medical.domain.KcdRepository;
import com.aimskr.ac2.hana.backend.core.medical.dto.DiagInfoExchangeDto;
import com.aimskr.ac2.hana.backend.core.medical.dto.KcdResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DiagInfoService {

    private final DiagInfoRepository diagInfoRepository;
    private final KcdRepository kcdRepository;


    public KcdResponseDto findKcdName(String kcdCd) {

        String kcdCdProcessed = kcdCd.replaceAll("[^A-Z0-9]", "");
        if (kcdCdProcessed.length() < 3) {
            log.debug("kcdCd length is not enough : {}", kcdCdProcessed);
            return null;
        }
        String kcdFinal = kcdCdProcessed.toUpperCase().substring(0, 3);
        if (kcdCdProcessed.length() > 3) {
            kcdFinal = kcdFinal + "." + kcdCdProcessed.substring(3);
        }
//        String kcdFinalProcessed = kcdFinal;
//
//
//        KcdResponseDto kcdResponseDto = kcdList.stream().filter(kcd -> kcdFinalProcessed.equals(kcd.getKcdCd())).findFirst().orElse(null);
//        log.debug("kcdCd : {}, kcdFinal : {}, kcdResponseDto : {}", kcdCd, kcdFinal, kcdResponseDto);
        Kcd kcd = kcdRepository.findByKcdCd(kcdFinal).orElse(null);

        if (kcd != null) {
            return new KcdResponseDto(kcd);
        } else {
            return null;
        }

    }

    public List<DiagInfoExchangeDto> getDiagInfoDto(String rqsReqId, String fileName) {

        List<DiagInfo> diagInfos = diagInfoRepository.findByFileName(rqsReqId, fileName);
        return diagInfos.stream().map(DiagInfoExchangeDto::new).toList();

    }

    @Transactional
    public void save(String rqsReqId, String accrNo, String dmSeqno, String fileName, List<DiagInfoExchangeDto> diagInfoExchangeDtos) {

        diagInfoRepository.deleteByAccrNoAndDmSeqnoAndFileName(accrNo, dmSeqno, fileName);

        for (DiagInfoExchangeDto diagInfoExchangeDto : diagInfoExchangeDtos) {
            DiagInfo diagInfo = diagInfoExchangeDto.toEntity();
            diagInfo.updateKey(rqsReqId, accrNo, dmSeqno, fileName);
            diagInfoRepository.save(diagInfo);

        }
    }

    public List<DiagInfo> getDiagInfos(String accrNo, String dmSeqno, String fileName) {
        return diagInfoRepository.findByAccrNoAndDmSeqnoAndFileName(accrNo, dmSeqno, fileName);


    }
}
