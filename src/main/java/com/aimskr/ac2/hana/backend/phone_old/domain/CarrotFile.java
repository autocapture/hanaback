package com.aimskr.ac2.hana.backend.phone_old.domain;

import com.aimskr.ac2.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(
        name = "carrot_file"
)
public class CarrotFile extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    // 사고번호 202301A0102
    @Column
    private String accrNo;

    // 이미지명 1672647244634.jpeg
    @Column
    private String imageName;

    @Column
    private String docType;

    @Column(columnDefinition = "TEXT")
    private String labelString;
}
