package com.aimskr.ac2.kakao.backend.security.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginFailure {
    String message;
    LocalDateTime timestamp;
}
