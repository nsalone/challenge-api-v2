package com.farmu.api.challenge.common.exception;

public class ImageResizeException extends RuntimeException {

    public ImageResizeException(final String message) {
        super(message);
    }

    public ImageResizeException(final RuntimeException cause) {
        super(cause.getMessage(), cause);
    }

}