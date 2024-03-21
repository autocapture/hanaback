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
