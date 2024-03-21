package com.aimskr.ac2.hana.backend.channel.json;

import com.aimskr.ac2.hana.backend.core.image.domain.Image;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ImageAcceptDto {

    private String imageDocumentId;

    @Builder
    public ImageAcceptDto(String imageDocumentId) {
        this.imageDocumentId = imageDocumentId;
    }

    public static ImageAcceptDto of(Image image){
        return ImageAcceptDto.builder()
                .imageDocumentId(image.getImgId())
                .build();
    }
}
