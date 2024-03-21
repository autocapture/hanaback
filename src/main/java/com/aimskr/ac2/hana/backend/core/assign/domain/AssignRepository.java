package com.aimskr.ac2.hana.backend.core.assign.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AssignRepository extends JpaRepository<Assign, Long> {

    @Query("SELECT a FROM Assign a where "
            + "(?1 is null or a.step = ?1) "
            + "and (?2 is null or a.acceptTime >= ?2) "
            + "and (?3 is null or a.acceptTime < ?3) "
            + "ORDER BY a.acceptTime ASC")
    List<Assign> findForManagement(String step, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM Assign a where a.accrNo = ?1 and a.dmSeqno = ?2")
    Optional<Assign> findByKey(String accrNo, String dmSeqno);

    // TODO: acceptTime -> requestTime
    @Query("SELECT a FROM Assign a where "
            + "(?1 is null or a.step = ?1) "
            + "and (?2 is null or a.qaOwner = ?2) "
            + "and (?3 is null or a.accidentType = ?3) "
            + "and (?4 is null or a.acceptTime >= ?4) "
            + "and (?5 is null or a.acceptTime < ?5) "
            + "ORDER BY a.acceptTime ASC")
    List<Assign> searchByAcceptDate(String step, String qaOwner, String accidentType, LocalDateTime fromTime, LocalDateTime toTime);

    @Query("SELECT a FROM Assign a where "
            + "(?1 is null or a.step = ?1) "
            + "and (?2 is null or a.qaOwner = ?2) "
            + "and (?3 is null or a.accidentType = ?3) "
            + "ORDER BY a.acceptTime ASC")
    List<Assign> searchByAccidentDate(String step, String qaOwner, String accidentType, LocalDateTime fromTime, LocalDateTime toTime);



//    @Query(value = "select date_format(a.accept_time, '%Y-%m') AS acceptMonth, " +
//            "date_format(a.accept_time, '%Y-%m-%d') AS acceptDate, " +
//            "date_format(a.result_delivery_time, '%Y-%m-%d') AS returnDate, " +
//            "a.receipt_no as receiptNo, " +
//            "a.receipt_seq as receiptSeq," +
//            "a.accident_type as accidentType," +
//            "a.result_accept_code as resultAcceptCode, " +
//            "CASE WHEN " +
//            "exists(select * from detail d where a.receipt_no = d.receipt_no and d.item_value <> '') " +
//            "then 'Y' Else 'N' END as INPUT " +
//            "from assign a where date(a.created_date) >= :startTime and date(a.created_date) <= :endTime",
//    nativeQuery = true)
//    List<BillingAssignDto> getInputAssign(@Param("startTime") LocalDateTime startTime,
//                                          @Param("endTime") LocalDateTime endTime);

}
