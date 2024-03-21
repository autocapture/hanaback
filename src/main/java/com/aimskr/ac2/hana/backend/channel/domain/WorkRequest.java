package com.aimskr.ac2.hana.backend.channel.domain;


import com.aimskr.ac2.common.enums.doc.AccidentType;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "work_request"
)
public class WorkRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String company;

    @Enumerated(EnumType.STRING)
    @Column
    private AccidentType accidentType;

    @Lob
    @Column
    private String requestJson;

}
