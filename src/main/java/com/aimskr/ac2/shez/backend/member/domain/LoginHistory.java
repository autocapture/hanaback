package com.aimskr.ac2.kakao.backend.member.domain;

import com.aimskr.ac2.kakao.backend.common.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@ToString
@Table(name = "login_history")
public class LoginHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime loginDt;  // 로그인 일시

    @Column(nullable = false, length = 20)
    private String account;    // 사용자ID

    @Column(nullable = false)
    private LoginType loginType;    // 로그인구분

    @Column(nullable = false, length = 20)
    private String ipAddr;  // IP주소

    @Column(nullable = false, length = 40)
    private String sessionId; // 세션ID

    @Builder
    public LoginHistory(LocalDateTime loginDt,
                        String account,
                        LoginType loginType,
                        String ipAddr,
                        String sessionId
                        ) {
        this.loginDt = loginDt;
        this.account = account;
        this.loginType = loginType;
        this.ipAddr = ipAddr;
        this.sessionId = sessionId;
    }
}
