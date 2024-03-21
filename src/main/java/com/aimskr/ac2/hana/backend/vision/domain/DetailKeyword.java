package com.aimskr.ac2.hana.backend.vision.domain;

import com.aimskr.ac2.common.enums.detail.ItemType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "detail_keyword"
)
public class DetailKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 항목의 값 인식을 위한 기준 키워드
    @Column
    private String detailKeyword;

    // 인식하고자하는 항목의 Type
    @Column
    @Enumerated(EnumType.STRING)
    private ItemType detailKeywordCategory;

    @Column
    private Integer priority;

}
