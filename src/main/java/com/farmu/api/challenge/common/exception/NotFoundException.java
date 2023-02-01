package com.farmu.api.challenge.common.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }

    public NotFoundException(final RuntimeException cause) {
        super(cause.getMessage(), cause);
    }

}