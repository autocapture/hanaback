package com.aimskr.ac2.hana.backend.channel.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(
        name = "image_hash"
)
public class ImageHash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String hash;
    @Column(length = 16)
    private String accrNo;
    @Column(length = 5)
    private String dmSeqno;
    @Column(length = 50)
    private String imageDocumentId;
}
