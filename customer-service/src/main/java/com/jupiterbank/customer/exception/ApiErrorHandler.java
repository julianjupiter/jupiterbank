package com.jupiterbank.customer.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author Julian Jupiter
 */
@RestControllerAdvice
public class ApiErrorHandler {
    private final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail customerNotFoundException(HttpServletRequest request, CustomerNotFoundException exception) {
        if (log.isDebugEnabled()) {
            log.debug("CUSTOMER_NOT_FOUND_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.NOT_FOUND;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Customer not found.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(InvalidCustomerIdException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail invalidCustomerIdException(HttpServletRequest request, InvalidCustomerIdException exception) {
        if (log.isDebugEnabled()) {
            log.debug("INVALID_CUSTOMER_ID_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid customer ID.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(DataValidationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail dataValidationException(HttpServletRequest request, DataValidationException exception) {
        if (log.isDebugEnabled()) {
            log.debug("DATA_VALIDATION_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid data request.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
        if (log.isDebugEnabled()) {
            log.debug("HTTP_MESSAGE_NOT_READABLE_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.BAD_REQUEST;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid request format.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", List.of(new ErrorDto(exception.getMessage())),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail otherException(HttpServletRequest request, Exception exception) {
        log.info("EXCEPTION", exception);
        if (log.isDebugEnabled()) {
            log.debug("EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Error encountered.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", List.of(new ErrorDto(exception.getMessage())),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }
}
