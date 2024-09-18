package com.jupiterbank.transaction.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * @author Julian Jupiter
 */
@RestControllerAdvice
public class ApiErrorHandler {
    private final Logger log = LoggerFactory.getLogger(ApiErrorHandler.class);

    @ExceptionHandler(TransactionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail transactionNotFoundException(HttpServletRequest request, TransactionNotFoundException exception) {
        if (log.isDebugEnabled()) {
            log.debug("TRANSACTION_NOT_FOUND_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.NOT_FOUND;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Transaction not found.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ProblemDetail accountNotFoundException(HttpServletRequest request, AccountNotFoundException exception) {
        if (log.isDebugEnabled()) {
            log.debug("ACCOUNT_NOT_FOUND_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.NOT_FOUND;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Account not found.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(InvalidAccountIdException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail invalidAccountIdException(HttpServletRequest request, InvalidAccountIdException exception) {
        if (log.isDebugEnabled()) {
            log.debug("INVALID_ACCOUNT_ID_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid account ID.");
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

    @ExceptionHandler(InvalidWithdrawalAmountException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail invalidWithdrawalAmountException(HttpServletRequest request, InvalidWithdrawalAmountException exception) {
        if (log.isDebugEnabled()) {
            log.debug("INVALID_WITHDRAWAL_AMOUNT_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid withdrawal amount.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", exception.getErrorDtos(),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(InvalidAccountStatusException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail invalidAccountStatusException(HttpServletRequest request, InvalidAccountStatusException exception) {
        if (log.isDebugEnabled()) {
            log.debug("INVALID_ACCOUNT_STATUS_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid account status.");
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

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail missingServletRequestParameterException(HttpServletRequest request, MissingServletRequestParameterException exception) {
        if (log.isDebugEnabled()) {
            log.debug("MISSING_SERVLET_REQUEST_PARAMETER_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.BAD_REQUEST;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid request query parameter.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", List.of(new ErrorDto(exception.getMessage())),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail dateTimeParseException(HttpServletRequest request, DateTimeParseException exception) {
        if (log.isDebugEnabled()) {
            log.debug("DATE_TIME_PARSE_EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.BAD_REQUEST;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Invalid date/time format.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", List.of(new ErrorDto(exception.getMessage())),
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
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(TransactionException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail transactionException(HttpServletRequest request, TransactionException exception) {
        if (log.isDebugEnabled()) {
            log.debug("TRANSACTION_EXCEPTION", exception);
        }

        var httpStatus = exception.getStatus();
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail("Error in transaction.");
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "errors", new ErrorDto(exception.getMessage()),
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail otherException(HttpServletRequest request, Exception exception) {
        if (log.isDebugEnabled()) {
            log.debug("EXCEPTION", exception);
        }

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(httpStatus.getReasonPhrase());
        problemDetail.setDetail(exception.getMessage());
        problemDetail.setInstance(URI.create(request.getRequestURI()));
        problemDetail.setProperties(Map.of(
                "createdAt", Instant.now()
        ));

        return problemDetail;
    }
}
