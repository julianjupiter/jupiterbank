package com.jupiterbank.account.dto;

import com.jupiterbank.account.entity.AccountType;
import com.jupiterbank.account.util.CustomerId;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
public record CreateAccountDto(
        @NotNull(message = "${NotNull.createAccountDto.accountType}")
        AccountType accountType,
        @NotNull(message = "${NotNull.createAccountDto.customerId}")
        CustomerId customerId,
        @NotNull(message = "${NotNull.createAccountDto.balance}")
        BigDecimal balance,
        @NotNull(message = "${NotNull.createAccountDto.dateOpened}")
        LocalDate dateOpened) {
}
