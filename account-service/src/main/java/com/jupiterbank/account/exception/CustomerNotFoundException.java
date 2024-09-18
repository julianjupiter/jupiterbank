package com.jupiterbank.account.exception;

import java.util.List;

/**
 * @author Julian Jupiter
 */
public class CustomerNotFoundException extends RuntimeException {
    private final List<ErrorDto> errorDtos;

    public CustomerNotFoundException(String message) {
        super(message);
        this.errorDtos = List.of(new ErrorDto(message));
    }

    public List<ErrorDto> getErrorDtos() {
        return errorDtos;
    }
}
