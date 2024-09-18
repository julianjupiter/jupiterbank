package com.jupiterbank.transaction.service;

import com.jupiterbank.transaction.dto.CreateTransactionDto;
import com.jupiterbank.transaction.dto.TransactionDto;
import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;

import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface TransactionService {
    Optional<TransactionDto> findByTransactionId(TransactionId transactionId);

    List<TransactionDto> findAll();

    List<TransactionDto> findByAccountNumber(AccountNumber accountNumber);

    List<TransactionDto> findByAccountNumberAndTransactionType(AccountNumber accountNumber, TransactionType transactionType);

    TransactionDto create(CreateTransactionDto createTransactionDto);
}
