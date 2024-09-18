package com.jupiterbank.account.exception;

import java.util.List;

/**
 * @author Julian Jupiter
 */
public class DataValidationException extends RuntimeException {
    private final List<ErrorDto> errorDtos;

    public DataValidationException(String message, List<ErrorDto> errorDtos) {
        super(message);
        this.errorDtos = errorDtos;
    }

    public List<ErrorDto> getErrorDtos() {
        return errorDtos;
    }
}
