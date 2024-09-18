package com.jupiterbank.account.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Julian Jupiter
 */
public class AccountException extends RuntimeException {
    private final List<ErrorDto> errorDtos;
    private final HttpStatus status;

    public AccountException(String message, HttpStatus status) {
        super(message);
        this.errorDtos = List.of(new ErrorDto(message));
        this.status = status;
    }

    public List<ErrorDto> getErrorDtos() {
        return errorDtos;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
