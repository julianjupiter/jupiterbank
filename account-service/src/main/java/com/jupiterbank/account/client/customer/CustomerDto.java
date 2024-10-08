package com.jupiterbank.account.client.customer;

import com.jupiterbank.account.util.CustomerId;

import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record CustomerDto(
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
