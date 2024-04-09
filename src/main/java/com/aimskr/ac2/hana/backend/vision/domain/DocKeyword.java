package com.aimskr.ac2.hana.backend.vision.domain;

import com.aimskr.ac2.common.enums.doc.AccidentType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(
        name = "doc_keyword"
)
public class DocKeyword{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 사고(청구)유형
    @Column
    @Enumerated(EnumType.STRING)
    private AccidentType accidentType;
    // 출현단어
    @Column(length = 45)
    private String word;
    // 대표단어
    @Column(length = 45)
    private String wordCategory;
    // 속성
    @Column(length = 45)
    private String attribute;
    // 정확도
    @Column
    private Integer confidence;

    // 4,'COMMON','type',1,'passport','passport'
    // (41,'MEDICAL','type',2,'수술확인서','surgery_certificate')
    //   `id` bigint(20) NOT NULL AUTO_INCREMENT,
    //  `accident_type` varchar(255) DEFAULT NULL,
    //  `attribute` varchar(45) DEFAULT NULL,
    //  `confidence` int(11) DEFAULT NULL,
    //  `word` varchar(45) DEFAULT NULL,
    //  `word_category` varchar(45) DEFAULT NULL,
    @Builder
    public DocKeyword(
            AccidentType accidentType,
            String wordCategory,
            String word,
            String attribute,
            int confidence
    ){
        this.accidentType = accidentType;
        this.wordCategory = wordCategory;
        this.word = word;
        this.attribute = attribute;
        this.confidence = confidence;
    }

}
