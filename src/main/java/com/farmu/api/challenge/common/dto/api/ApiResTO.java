package com.farmu.api.challenge.common.dto.api;

import com.farmu.api.challenge.common.dto.entity.ReferenceTO;
import com.farmu.api.challenge.common.utils.ValidationUtils;
import lombok.Getter;

@Getter
public class ApiResTO<T> {

    private ApiResTO(final ApiResult result, final T detail) {
        this.result = result;
        this.detail = detail;
    }

    private final ApiResult result;
    private final T detail;

    public static ApiResTO<ReferenceTO> refResponse(final ReferenceTO reference) {
        ValidationUtils.checkArg(reference, "reference");
        return new ApiResTO<>(ApiResult.OK, reference);
    }

    public static ApiResTO<ApiErrorTO> errorResponse(final ApiErrorReason reason, final String message) {
        ValidationUtils.checkArg(reason, "reason");
        ValidationUtils.checkArg(message, "message");
        return new ApiResTO<>(ApiResult.ERROR, ApiErrorTO.newApiError(reason, message));
    }

}