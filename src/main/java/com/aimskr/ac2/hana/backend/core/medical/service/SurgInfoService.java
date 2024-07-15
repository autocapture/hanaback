package com.aimskr.ac2.hana.backend.core.medical.service;


import com.aimskr.ac2.hana.backend.core.medical.domain.*;
import com.aimskr.ac2.hana.backend.core.medical.dto.SurgInfoExchangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SurgInfoService {

    private final SurgInfoRepository surgInfoRepository;

    public List<SurgInfoExchangeDto> getSurgInfo(String rqsReqId, String fileName) {

        List<SurgInfo> surgInfos = surgInfoRepository.findByFileName(rqsReqId, fileName);
        return surgInfos.stream().map(SurgInfoExchangeDto::new).toList();

    }

    @Transactional
    public void save(String rqsReqId, String accrNo, String dmSeqno, String fileName, List<SurgInfoExchangeDto> surgInfoExchangeDtos) {

        surgInfoRepository.deleteByAccrNoAndDmSeqnoAndFileName(accrNo, dmSeqno, fileName);

        for (SurgInfoExchangeDto surgInfoExchangeDto : surgInfoExchangeDtos) {
            SurgInfo surgInfo = surgInfoExchangeDto.toEntity();
            surgInfo.updateKey(rqsReqId, accrNo, dmSeqno, fileName);
            surgInfoRepository.save(surgInfo);

        }
    }

}
