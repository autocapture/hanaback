package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.common.enums.status.AcceptStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AcceptResultDto {
    private String status;
    private String message;
    public static AcceptResultDto of (AcceptStatus acceptStatus) {
        return AcceptResultDto.builder()
                .status(acceptStatus.getCode())
                .message(acceptStatus.getMessage())
                .build();
    }
}
