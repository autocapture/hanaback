package com.aimskr.ac2.common.health.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloResponseDto {
    private String status;
    private String reply;
}
