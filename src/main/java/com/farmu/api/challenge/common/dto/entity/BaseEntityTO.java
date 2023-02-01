package com.farmu.api.challenge.common.dto.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
public abstract class BaseEntityTO {

    private Long id;
    private LocalDateTime created;
    private LocalDateTime updated;

}