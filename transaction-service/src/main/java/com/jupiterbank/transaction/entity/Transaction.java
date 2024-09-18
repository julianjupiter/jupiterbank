package com.jupiterbank.transaction.entity;

import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = TransactionIdConverter.class)
    private TransactionId transactionId;
    @Convert(converter = AccountIdConverter.class)
    private AccountNumber accountNumber;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private BigDecimal amount;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public TransactionId getTransactionId() {
        return transactionId;
    }

    public Transaction setTransactionId(TransactionId transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public Transaction setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Transaction setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Transaction setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Transaction setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Transaction setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
