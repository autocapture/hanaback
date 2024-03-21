package com.aimskr.ac2.hana.backend.core.phone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhoneRepairDetailRepository extends JpaRepository<PhoneRepairDetail, Long> {

    @Query("SELECT k FROM PhoneRepairDetail k WHERE k.accrNo = ?1 AND k.dmSeqno = ?2 AND k.fileName = ?3")
    List<PhoneRepairDetail> findByKeyAndFileName(String accrNo, String dmSeqno, String fileName);
}
