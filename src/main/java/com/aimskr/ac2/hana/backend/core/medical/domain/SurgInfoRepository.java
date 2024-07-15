package com.aimskr.ac2.hana.backend.core.medical.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurgInfoRepository extends JpaRepository<SurgInfo, Long> {

    @Query("select d from SurgInfo d where d.rqsReqId = ?1 and d.fileName = ?2")
    List<SurgInfo> findByFileName(String rqsReqId, String fileName);

    void deleteByAccrNoAndDmSeqnoAndFileName(String accrNo, String dmSeqno, String fileName);
}
