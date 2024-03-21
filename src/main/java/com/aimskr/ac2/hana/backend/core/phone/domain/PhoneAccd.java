package com.aimskr.ac2.hana.backend.core.phone.domain;

import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import com.aimskr.ac2.common.domain.BaseTimeEntity;
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
        name = "phone_accd"
)
public class PhoneAccd extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private Long id;

    // 전문 Key : 파일송수신일련번호, 팩스일련번호, 전송회차
    // 제조사
    @Column
    private String manufacturer;
    // 표준코드
    @Column
    private String stdCd;
    // 부품코드
    @Column
    private String accdCd;
    // 부품명
    @Column
    private String accdNm;
    // 종류
    @Column
    private String accdCategory;
    // 심사의견 코드
    @Column
    private String accdType;
    // 심사의견 명칭
    @Column
    private String accdTypeDtl;
    // 사유
    @Column
    private String accdTypeNm;

}
