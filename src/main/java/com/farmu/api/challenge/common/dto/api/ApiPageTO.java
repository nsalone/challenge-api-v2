package com.farmu.api.challenge.common.dto.api;

import com.farmu.api.challenge.common.dto.enums.PageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiPageTO<T> {

    private List<T> elements;
    private long total;
    private PageType type;

    public ApiPageTO(final List<T> elements) {
        this.elements = elements;
    }
}