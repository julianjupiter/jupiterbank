package com.jupiterbank.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record UpdateCustomerDto(
        @NotBlank(message = "${NotBlank.updateCustomerDto.lastName}")
        String lastName,
        @NotBlank(message = "${NotBlank.updateCustomerDto.firstName}")
        String firstName,
        @NotBlank(message = "${NotBlank.updateCustomerDto.middleName}")
        String middleName,
        String extensionName,
        @NotNull(message = "${NotNull.updateCustomerDto.dateOfBirth}")
        LocalDate dateOfBirth,
        @NotBlank(message = "${NotBlank.updateCustomerDto.contactNumber}")
        String contactNumber,
        @NotBlank(message = "${NotBlank.updateCustomerDto.emailAddress}")
        String emailAddress,
        @NotBlank(message = "${NotBlank.updateCustomerDto.address}")
        String address) {
}
