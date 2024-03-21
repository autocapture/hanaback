package com.aimskr.ac2.hana.backend.core.phone.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface PhoneRepairRepository extends JpaRepository<PhoneRepair, Long>{
    @Query("SELECT k FROM PhoneRepair k WHERE k.accrNo = ?1 AND k.dmSeqno = ?2")
    List<PhoneRepair> findByKey(String accrNo, String dmSeqno);

    @Query("SELECT k FROM PhoneRepair k WHERE k.accrNo = ?1 AND k.dmSeqno = ?2 AND k.fileName = ?3")
    List<PhoneRepair> findByKeyAndFileName(String accrNo, String dmSeqno, String fileName);

    @Query("SELECT k FROM PhoneRepair k WHERE k.fileName = ?1")
    List<PhoneRepair> findByFileName(String fileName);

    @Query("SELECT k FROM PhoneRepair k WHERE k.accrNo = ?1 and k.dmSeqno = ?2 and k.itemName = ?3")
    List<PhoneRepair> findItemValueByItemName(String accrNo, String dmSeqno, String itemName);

    @Query("SELECT k From PhoneRepair k WHERE k.fileName != ?1 and k.itemName = ?2 and k.itemValue = ?3 and k.createdDate < ?4 and k.accuracy > 0.8 order by k.createdDate")
    List<PhoneRepair> findByItemNameAndItemValue(String fileName, String itemName, String itemValue, LocalDateTime createdDate);


}
