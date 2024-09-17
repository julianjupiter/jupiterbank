package com.jupiterbank.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record CreateCustomerDto(
        @NotBlank(message = "${NotBlank.createCustomerDto.lastName}")
        String lastName,
        @NotBlank(message = "${NotBlank.createCustomerDto.firstName}")
        String firstName,
        @NotBlank(message = "${NotBlank.createCustomerDto.middleName}")
        String middleName,
        String extensionName,
        @NotNull(message = "${NotNull.createCustomerDto.dateOfBirth}")
        LocalDate dateOfBirth,
        @NotBlank(message = "${NotBlank.createCustomerDto.contactNumber}")
        String contactNumber,
        @NotBlank(message = "${NotBlank.createCustomerDto.emailAddress}")
        String emailAddress,
        @NotBlank(message = "${NotBlank.createCustomerDto.address}")
        String address) {
}
