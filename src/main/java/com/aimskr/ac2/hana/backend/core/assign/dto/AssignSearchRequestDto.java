package com.aimskr.ac2.hana.backend.core.assign.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignSearchRequestDto {
    private String qaOwner;
    private String step;
    private String accidentType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String searchType;
}
