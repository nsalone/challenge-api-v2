package com.farmu.api.challenge.domain.shortener_url.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortenerUrl implements Serializable {

    private String id;

    private String url;

}
