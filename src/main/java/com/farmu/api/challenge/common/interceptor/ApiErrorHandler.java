package com.farmu.api.challenge.common.interceptor;

import com.farmu.api.challenge.common.dto.api.ApiErrorReason;
import com.farmu.api.challenge.common.dto.api.ApiErrorTO;
import com.farmu.api.challenge.common.dto.api.ApiResTO;
import com.farmu.api.challenge.common.exception.BadRequestException;
import com.farmu.api.challenge.common.exception.NotFoundException;
import com.farmu.api.challenge.common.utils.CollectionUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.lang.annotation.ElementType;
import java.util.Optional;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.ResponseEntity.unprocessableEntity;

@Slf4j
@RestControllerAdvice
public class ApiErrorHandler extends ResponseEntityExceptionHandler {


    /**
     * 400.
     * Handle validation exceptions when can not deserialize jsons. For example when a enum value not exists.
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            @NonNull final HttpMessageNotReadableException exception,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.warn("HTTP message not readable exception: '{}'", exception.getMessage());
        final Throwable rootException = ofNullable(exception.getRootCause()).orElse(exception);
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, rootException.getMessage()));
    }


    /**
     * 400.
     * Handle validation annotations on DTOs.
     */
    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull final MethodArgumentNotValidException exception,
            @NonNull final HttpHeaders headers,
            @NonNull final HttpStatus status,
            @NonNull final WebRequest request
    ) {
        log.warn("Validation exception. Extracting message...");
        final String message = CollectionUtils.safeStream(exception.getBindingResult().getAllErrors())
                .map(this::extractMessage)
                .collect(joining(". "));
        log.warn("Validation exception: '{}'", message);
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, message));
    }

    @SuppressWarnings("rawtypes")
    private String extractMessage(final ObjectError violation) {
        if (isNull(violation)) {
            return "No violation present";
        }
        final ElementType elementType = Optional.of(violation)
                .map(it -> it.unwrap(ConstraintViolationImpl.class))
                .map(it -> (ConstraintDescriptorImpl) it.getConstraintDescriptor())
                .map(ConstraintDescriptorImpl::getElementType)
                .orElse(FIELD);
        if (METHOD.equals(elementType)) {
            return violation.getDefaultMessage();
        }
        return CollectionUtils.safeArrayToList(violation.getArguments()).stream()
                .findFirst()
                .map(it -> (DefaultMessageSourceResolvable) it)
                .map(it -> String.format("Field '%s' %s", CollectionUtils.safeArrayToList(it.getCodes()).stream().findFirst().orElse(EMPTY), violation.getDefaultMessage()))
                .orElseGet(violation::getDefaultMessage);
    }


    /**
     * 400.
     * Handle validation exceptions in internal code by converter validation|s
     * * MethodArgumentTypeMismatchException: thrown when there is an invalid param on URL.
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApiResTO<ApiErrorTO>> validationException(
            @NonNull final Exception exception
    ) {
        log.warn("Validation exception: '{}'", exception.getMessage());
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, exception.getMessage()));
    }


    /**
     * 400.
     * Handle validation exceptions in internal code by converter validation|s
     * * ConstraintViolationException: thrown when there is an invalid param on URL.
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<ApiResTO<ApiErrorTO>> handleConstraintViolation(
            final ConstraintViolationException exception
    ) {
        log.warn("Constraint violation exception: '{}'", exception.getMessage());
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, exception.getMessage()));
    }


    /**
     * 400.
     * Handle validation exceptions in internal code by converter validation|s
     * * MethodArgumentTypeMismatchException: thrown when there is an invalid param on URL.
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ApiResTO<ApiErrorTO>> badRequestException(
            final BadRequestException exception
    ) {
        log.warn("Bad Request exception: '{}'", exception.getMessage());
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, exception.getMessage()));
    }


    /**
     * 422.
     * Not found exception.
     */
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResTO<ApiErrorTO>> notFoundException(final NotFoundException exception) {
        log.warn("Not found exception: '{}'", exception.getMessage());
        return unprocessableEntity().body(ApiResTO.errorResponse(ApiErrorReason.NOT_FOUND, exception.getMessage()));
    }

    /**
     * 500.
     * Handle unknown exceptions.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResTO<ApiErrorTO>> unhandledException(
            final Exception exception
    ) {
        log.error("Unknown exception: ", exception);
        return status(INTERNAL_SERVER_ERROR).body(ApiResTO.errorResponse(ApiErrorReason.UNKNOWN, "Unknown error"));
    }

    /**
     *
     * @param exception
     * @return
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResTO<ApiErrorTO>> illegalArgumentException(
            final Exception exception
    ) {
        log.error("Illegal argument exception: ", exception);
        return badRequest().body(ApiResTO.errorResponse(ApiErrorReason.INVALID_PARAMETER, exception.getMessage()));
    }

}