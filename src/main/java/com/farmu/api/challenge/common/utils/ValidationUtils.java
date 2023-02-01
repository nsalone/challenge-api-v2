package com.farmu.api.challenge.common.utils;


import com.google.common.base.Preconditions;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Util functions for validations of objects.
 */
public class ValidationUtils {

    public static final URLValidator INSTANCE = new URLValidator();
    private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);


    /**
     * Check if a param has value and url is valid. Throws IllegalArgumentException if not.
     */
    public static void validateURL(String url, String paramName) {
        Matcher m = URL_PATTERN.matcher(url);
        Preconditions.checkArgument(hasContent(url) && m.matches(), String.format("Param '%s' is invalid", paramName));
    }


    /**
     * Check if a param has value. Throws IllegalArgumentException if not.
     */
    public static void checkArg(final Object param, final String paramName) {
        Preconditions.checkArgument(hasContent(param), String.format("Param '%s' is mandatory", paramName));
    }

    /**
     * Check if a param numeric has value. Throws IllegalArgumentException if not.
     */
    public static void checkArg(final int param, final String paramName) {
        Preconditions.checkArgument(greaterThanZero(param), String.format("Param '%s' is mandatory", paramName));
    }

    /**
     * Evaluates if number greater than zero.
     */
    public static boolean greaterThanZero(final int value) {
        return value > 0;
    }

    /**
     * Evaluates if object has content.
     */
    public static boolean hasContent(final Object object) {
        if (isNull(object)) {
            return FALSE;

        } else if ((object instanceof String)) {
            return isNotBlank(((String) object));

        } else {
            return TRUE;
        }
    }

//    /**
//     * Check if the provider error was because a specific reason.
//     */
//    public static void checkErrorReason(final ProviderException e, final HttpStatus httpStatus, final String reason) {
//        if (!httpStatus.equals(e.getRequest().getHttpStatus()) || !reason.equals(e.getRequest().getErrorReason())) {
//            throw e;
//        }
//    }

}