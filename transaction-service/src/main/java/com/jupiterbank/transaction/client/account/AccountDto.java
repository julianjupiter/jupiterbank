package com.jupiterbank.transaction.client.account;

import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.CustomerId;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record AccountDto(
        AccountNumber accountNumber,
        AccountType accountType,
        CustomerId customerId,
        BigDecimal balance,
        LocalDate dateOpened,
        LocalDate dateClosed,
        AccountStatus accountStatus,
        Instant createdAt,
        Instant updatedAt) {
}
