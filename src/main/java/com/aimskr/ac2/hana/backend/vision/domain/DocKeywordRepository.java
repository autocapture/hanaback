package com.aimskr.ac2.hana.backend.vision.domain;

import com.aimskr.ac2.common.enums.doc.AccidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface DocKeywordRepository extends JpaRepository<DocKeyword, Long> {
    Optional<DocKeyword> findDocKeywordByWord(String word);


    @Query("SELECT a FROM DocKeyword a where a.accidentType = ?1 or a.accidentType = com.aimskr.ac2.common.enums.doc.AccidentType.COMMON")
    List<DocKeyword> findByAccidentType(AccidentType accidentType);

}
