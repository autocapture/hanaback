package com.aimskr.ac2.kakao.backend.member.domain;

import com.aimskr.ac2.kakao.backend.common.enums.Constant;
import com.aimskr.ac2.kakao.backend.common.domain.BaseTimeEntity;
import com.aimskr.ac2.kakao.backend.member.dto.MemberRequestDto;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

// LOMBOK
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Audited
@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
    public static final int MAX_PWD_REMAINING_DAY = 90;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 아이디: '',
    @Column(nullable = false, length = 20)
    private String account;

    // name: '',
    @Column(nullable = false, length = 12)
    private String name;

    // birth: '',
    @Column
    private LocalDate birth;

    // phone: '',
    @Column(length = 20)
    private String phone;

    // password: '',
    @Column(nullable = false, length=80)
    private String password;

    // role: '배당관리, 정보입력, QA',
    @Enumerated(EnumType.STRING)
    private MemberRole role;


    @Column(columnDefinition = "boolean default false")
    private Boolean isQaAssign;

    @Column(columnDefinition = "boolean default false")
    private Boolean isLock; // 비밀번호 잠금 여부

    @Column(columnDefinition = "boolean default false")
    private Boolean shouldModi;  // 비밀번호 재설정 필요 여부

    @Column
    private Integer pwdRemainingDay; // 패스워드 변경 잔여일


    @Column(length = 40)
    private String email;   // 이메일주소

    @Column
    private Integer countFailure; // 로그인 실패횟수

    public void update(MemberRequestDto memberRequestDto) {
        this.account = memberRequestDto.getAccount();
        this.name = memberRequestDto.getName();
        this.birth = memberRequestDto.getBirth();
        this.phone = memberRequestDto.getPhone();
        // this.password = memberRequestDto.getPassword();
        this.role = memberRequestDto.getRole();
        this.isQaAssign = memberRequestDto.getIsQaAssign();
        this.email = memberRequestDto.getEmail();
        this.countFailure = 0;
    }

    /**
     * 최초 등록시 멤버 기본 설정
     */
    public void setDefaultValue() {
        this.isLock = false;    // 비밀번호 잠금 false
        this.shouldModi = true; // 비밀번호 재설정 true
        this.pwdRemainingDay = MAX_PWD_REMAINING_DAY;  // 패스워드 변경 잔여일 90일
        this.countFailure = 0;
    }

    /**
     * 계정 잠김 여부 업데이트
     */
    public void updateIsLock(boolean isLock) {
        this.isLock = isLock;
    }

    /**
     * 암호화된 패스워드로 변경 (패스워드 암호화)
     */
    public void encodingPassword(String password) {
        this.password = password;
        this.isLock = false;
        this.countFailure = 0;
    }

    /**
     * 로그인 실패횟수 업데이트
     */
    public void updateCountFailure(Integer countFailure) {
        this.countFailure = countFailure;
        if (countFailure >= Constant.MAX_FAILURE_CNT) {
            this.isLock = true;
        }
    }

    /**
     * 패스워드 유효기간 1일 감소
     */
    public void reducePwdRemainingDay() {
        this.pwdRemainingDay = this.pwdRemainingDay - 1;
    }


    /**
     * QA 배당가능여부 업데이트
     */
    public void updateIsQaAssign(Boolean isQaAssign) { this.isQaAssign = isQaAssign; }

}
