package com.jupiterbank.transaction.repository;

import com.jupiterbank.transaction.entity.Transaction;
import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByTransactionId(TransactionId transactionId);

    List<Transaction> findByAccountNumber(AccountNumber accountNumber);

    List<Transaction> findByAccountNumberAndTransactionType(AccountNumber accountNumber, TransactionType transactionType);
}
