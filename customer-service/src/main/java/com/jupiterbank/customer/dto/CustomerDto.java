package com.jupiterbank.customer.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jupiterbank.customer.util.CustomerId;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record CustomerDto(
        @JsonSerialize(using = ToStringSerializer.class)
        CustomerId customerId,
        String lastName,
        String firstName,
        String middleName,
        String extensionName,
        LocalDate dateOfBirth,
        String contactNumber,
        String emailAddress,
        String address,
        Instant createdAt,
        Instant updatedAt) {
}
