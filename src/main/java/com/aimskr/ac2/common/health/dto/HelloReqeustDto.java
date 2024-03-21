package com.aimskr.ac2.common.health.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloReqeustDto {
    private String person;
    private String message;
}
