package com.farmu.api.challenge.common.dto.entity;

import com.farmu.api.challenge.common.utils.ValidationUtils;
import lombok.Builder;
import lombok.Getter;

import static java.lang.String.format;

@Getter
@Builder
public class ReferenceTO {

    private ReferenceTO(final EntityType entityType, final ReferenceType referenceType, final String referenceId) {
        this.entityType = entityType;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
    }

    private final EntityType entityType;
    private final ReferenceType referenceType;
    private final String referenceId;

    public static ReferenceTO newReference(final EntityType entityType, final ReferenceType referenceType, final String referenceId) {
        ValidationUtils.checkArg(entityType, "entity-type");
        ValidationUtils.checkArg(referenceType, "reference-type");
        ValidationUtils.checkArg(referenceId, "reference-id");
        return new ReferenceTO(entityType, referenceType, referenceId);
    }

    public String shortString() {
        return format("%s-%s-%s", this.entityType, this.referenceType, this.referenceId);
    }

}