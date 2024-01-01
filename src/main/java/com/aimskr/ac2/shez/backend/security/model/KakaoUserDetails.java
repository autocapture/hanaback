package com.aimskr.ac2.kakao.backend.security.model;

import com.aimskr.ac2.kakao.backend.member.domain.Member;
import com.aimskr.ac2.kakao.backend.member.domain.MemberRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * SpringSecurity UserDetails
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserDetails implements UserDetails {
    private String account;
    private String name;
    private String password;
    private MemberRole role;
    private Boolean isLock;
    private Integer pwdRemainingDay;
    private LocalDate birth;
    private Boolean isWrongBirth;
    private Integer countFailure;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.name();
            }
        });
        return authList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.pwdRemainingDay == null? false : this.pwdRemainingDay.intValue() > 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLock == null? false : !isLock;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static KakaoUserDetails of(Member member) {
        return KakaoUserDetails.builder()
                .account(member.getAccount())
                .name(member.getName())
                .password(member.getPassword())
                .role(member.getRole())
                .isLock(member.getIsLock())
                .pwdRemainingDay(member.getPwdRemainingDay())
                .birth(member.getBirth())
                .isWrongBirth(false)
                .countFailure(member.getCountFailure())
                .build();
    }
}
