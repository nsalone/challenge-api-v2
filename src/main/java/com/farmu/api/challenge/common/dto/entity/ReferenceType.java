package com.farmu.api.challenge.common.dto.entity;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum ReferenceType {

    USER_NUMBER,
    LEAD_CODE,
    CLIENT_ID,
    BRANCH_CODE,
    CITY_DATE_CODE,
    REQUEST_ID,
    CAMPAIGN_ID,
    NUMBER,
    CODE,
    EXTERNAL_ID,
    SELLER_EMAIL,
    CLUSTER_CODE;

    public static ReferenceType map(final String name) {
        return stream(ReferenceType.values()).filter(it -> it.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static ReferenceType map(final String entity, final String code) {
        String name = format("%s_%s", entity, code);
        return stream(ReferenceType.values()).filter(it -> it.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

}