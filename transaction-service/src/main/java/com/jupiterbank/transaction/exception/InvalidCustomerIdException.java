package com.jupiterbank.transaction.exception;

import java.util.List;

/**
 * @author Julian Jupiter
 */
public class InvalidCustomerIdException extends RuntimeException {
    private final List<ErrorDto> errorDtos;

    public InvalidCustomerIdException(String message) {
        super(message);
        this.errorDtos = List.of(new ErrorDto(message));
    }

    public List<ErrorDto> getErrorDtos() {
        return errorDtos;
    }
}
