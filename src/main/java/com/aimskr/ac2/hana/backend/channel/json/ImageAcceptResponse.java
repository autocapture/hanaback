package com.aimskr.ac2.hana.backend.channel.json;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageAcceptResponse {

    private String documentName;
    private String checkSum;

    @Builder
    public ImageAcceptResponse(String documentName,
                               String checkSum){
        this.documentName = documentName;
        this.checkSum = checkSum;
    }

}
