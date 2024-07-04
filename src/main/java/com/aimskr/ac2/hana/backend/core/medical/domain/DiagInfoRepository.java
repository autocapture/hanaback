package com.aimskr.ac2.hana.backend.core.medical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiagInfoRepository extends JpaRepository<DiagInfo, Long> {

    @Query("select d from DiagInfo d where d.rqsReqId = ?1 and d.fileName = ?2")
    List<DiagInfo> findByFileName(String rqsReqId, String fileName);

    void deleteByAccrNoAndDmSeqnoAndFileName(String accrNo, String dmSeqno, String fileName);
}
