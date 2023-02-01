package com.farmu.api.challenge.common.dto.api;

import com.farmu.api.challenge.common.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class ApiErrorTO {

    private ApiErrorTO(final ApiErrorReason reason, final String message) {
        this.reason = reason;
        this.message = message;
    }

    private final ApiErrorReason reason;
    private final String message;

    public static ApiErrorTO newApiError(final ApiErrorReason reason, final String message) {
        ValidationUtils.checkArg(reason, "reason");
        return new ApiErrorTO(reason, message);
    }

}