package com.jupiterbank.account.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jupiterbank.account.entity.AccountStatus;
import com.jupiterbank.account.entity.AccountType;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record AccountDto(
        @JsonSerialize(using = ToStringSerializer.class)
        AccountNumber accountNumber,
        AccountType accountType,
        @JsonSerialize(using = ToStringSerializer.class)
        CustomerId customerId,
        BigDecimal balance,
        LocalDate dateOpened,
        LocalDate dateClosed,
        AccountStatus accountStatus,
        Instant createdAt,
        Instant updatedAt) {
}
