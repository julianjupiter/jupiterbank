package com.jupiterbank.transaction.dto;

import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.util.AccountNumber;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @author Julian Jupiter
 */
public record CreateTransactionDto(
        @NotNull(message = "${NotNull.createTransactionDto.accountNumber}")
        AccountNumber accountNumber,
        @NotNull(message = "${NotNull.createTransactionDto.transactionType}")
        TransactionType transactionType,
        @NotNull(message = "${NotNull.createTransactionDto.amount}")
        BigDecimal amount) {
}
