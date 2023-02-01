package com.farmu.api.challenge.domain.image_resizer.dto;

import com.farmu.api.challenge.common.dto.entity.BaseEntityTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageTO extends BaseEntityTO {

    private String fileName;

    private String fileType;

    private int targetWidth;

    private int targetHeight;

    private String uuid;

    private Status status;

    @JsonIgnore
    private byte[] data;

    @JsonIgnore
    private byte[] dataResize;

}
