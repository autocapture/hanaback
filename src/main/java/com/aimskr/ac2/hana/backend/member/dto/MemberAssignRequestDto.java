package com.aimskr.ac2.hana.backend.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 배당관리 화면에서 배당 여부를 직접 수정하기 위한 DTO
 * 분류, 입력, QA 공통으로 사용
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberAssignRequestDto {
    private Long memberId;
    private Boolean isAssign;

    public MemberAssignRequestDto(Long memberId, Boolean isAssign) {
        this.memberId = memberId;
        this.isAssign = isAssign;
    }
}
