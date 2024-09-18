package com.jupiterbank.transaction.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionDto(
        @JsonSerialize(using = ToStringSerializer.class)
        TransactionId transactionId,
        @JsonSerialize(using = ToStringSerializer.class)
        AccountNumber accountNumber,
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal balance,
        Instant createdAt,
        Instant updatedAt) {
}
