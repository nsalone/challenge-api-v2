package com.farmu.api.challenge.domain.image_resizer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private String uuid;

    private String fileName;

    private String fileType;

    private long size;

}
