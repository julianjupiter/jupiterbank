package com.jupiterbank.transaction.mapper;

import com.jupiterbank.transaction.dto.CreateTransactionDto;
import com.jupiterbank.transaction.dto.TransactionDto;
import com.jupiterbank.transaction.entity.Transaction;
import com.jupiterbank.transaction.util.TransactionId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@Component
public class TransactionMapper {
    public TransactionDto map(Transaction transaction) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                null,
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
    public TransactionDto map(Transaction transaction, BigDecimal newBalance) {
        return new TransactionDto(
                transaction.getTransactionId(),
                transaction.getAccountNumber(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                newBalance,
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }

    public Transaction map(CreateTransactionDto createTransactionDto) {
        return new Transaction()
                .setTransactionId(TransactionId.create())
                .setAccountNumber(createTransactionDto.accountNumber())
                .setTransactionType(createTransactionDto.transactionType())
                .setAmount(createTransactionDto.amount())
                .setCreatedAt(Instant.now());
    }
}
