package com.farmu.api.challenge.common.dto.internal;

import com.farmu.api.challenge.common.dto.enums.PageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;

@Data
@Value
@Builder
@AllArgsConstructor
public class PageTO<T> {

    private PageType type;
    private List<T> elements;


}