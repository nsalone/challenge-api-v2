package com.farmu.api.challenge.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionRequestTO {

    /**
     * Invoked Provider.
     */
    private ProviderType provider;

    /**
     * HTTP method.
     */
    private String httpMethod;

    /**
     * Invoked URL.
     */
    private String url;

    /**
     * Response HTTP status.
     */
    private HttpStatus httpStatus;

    /**
     * Response message.
     */
    private String message;

    /**
     * Response error reason.
     */
    private String errorReason;

    /**
     * Response body.
     */
    private String responseBody;


    /**
     * Obtain full description of request.
     */
    public String fullToString() {
        return format("Error invoking '%s' '(%s) %s' - Response '%s - %s' - Body '%s'", provider, httpMethod, url, httpStatus, message, responseBody);
    }

    /**
     * Obtain full description of request.
     */
    public String hiddenToString() {
        return format("Error invoking '%s' - Response message '%s'", provider, message);
    }

}