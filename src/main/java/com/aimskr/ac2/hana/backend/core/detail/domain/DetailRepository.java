package com.aimskr.ac2.hana.backend.core.detail.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DetailRepository extends JpaRepository<Detail, Long>{
    @Query("SELECT k FROM Detail k WHERE k.rqsReqId = ?1 AND k.accrNo = ?2 AND k.dmSeqno = ?3")
    List<Detail> findByKey(String rqsReqId, String accrNo, String dmSeqno);

    @Query("SELECT k FROM Detail k WHERE k.rqsReqId = ?1 AND k.accrNo = ?2 AND k.dmSeqno = ?3 AND k.fileName = ?4")
    List<Detail> findByKeyAndFileName(String rqsReqId, String accrNo, String dmSeqno, String fileName);

    @Query("SELECT k FROM Detail k WHERE k.rqsReqId = ?1 AND k.fileName = ?2")
    List<Detail> findByFileName(String rqsReqId, String fileName);

    @Query("SELECT k FROM Detail k WHERE k.accrNo = ?1 and k.dmSeqno = ?2 and k.itemName = ?3")
    List<Detail> findItemValueByItemName(String accrNo, String dmSeqno, String itemName);

    @Query("SELECT k From Detail k WHERE k.fileName != ?1 and k.itemName = ?2 and k.itemValue = ?3 and k.createdDate < ?4 order by k.createdDate")
    List<Detail> findByItemNameAndItemValue(String fileName, String itemName, String itemValue, LocalDateTime createdDate);

}
