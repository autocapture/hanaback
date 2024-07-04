package com.aimskr.ac2.hana.backend.vision.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AiDetailRepository extends JpaRepository<AiDetail, Long> {
    @Query("select a from AiDetail a where a.rqsReqId = ?1 and a.accrNo = ?2 and a.dmSeqno = ?3 and a.fileName = ?4")
    List<AiDetail> findByKeyAndFileName(String rqsReqId, String accrNo, String dmSeqno, String fileName);
}
