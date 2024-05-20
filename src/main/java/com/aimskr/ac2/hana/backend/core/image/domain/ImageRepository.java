package com.aimskr.ac2.hana.backend.core.image.domain;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT a FROM Image a where a.rqsReqId = ?1")
    List<Image> findByReqId(String reqId);

    @Query("SELECT a FROM Image a where a.rqsReqId = ?1 and a.accrNo = ?2 and a.dmSeqno = ?3")
    List<Image> findByKey(String rqsReqId, String accrNo, String dmSeqno);

    @Query("SELECT a FROM Image a where a.accrNo = ?1 and a.dmSeqno = ?2 and a.isDup = false order by a.sequence asc")
    List<Image> findByKeyForDupCheck(String accrNo, String dmSeqno);

    @Query("SELECT a FROM Image a where a.rqsReqId = ?1 and a.accrNo = ?2 and a.dmSeqno = ?3 and a.fileName = ?4")
    Optional<Image> findByKeyAndFileName(String rqsReqId, String accrNo, String dmSeqno, String fileName);

    @Query("SELECT a FROM Image a where a.rqsReqId = ?1 and a.fileName = ?2")
    Optional<Image> findByFileName(String rqsReqId, String fileName);

}
