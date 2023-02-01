package com.farmu.api.challenge.common.dto.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponseTO {

    public static ApiResponseTO okResponse() {
        return new ApiResponseTO("OK");
    }

    public static ApiResponseTO error() {
        return new ApiResponseTO("ERROR");
    }

    private String message;

}