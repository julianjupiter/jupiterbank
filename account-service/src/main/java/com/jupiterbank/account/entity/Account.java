package com.jupiterbank.account.entity;

import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Convert(converter = AccountIdConverter.class)
    private AccountNumber accountNumber;
    @Convert(converter = CustomerIdConverter.class)
    private CustomerId customerId;
    private BigDecimal balance;
    private LocalDate dateOpened;
    private LocalDate dateClosed;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Instant createdAt;
    private Instant updatedAt;

    public Long getId() {
        return id;
    }

    public Account setId(Long id) {
        this.id = id;
        return this;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Account setAccountType(AccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public AccountNumber getAccountNumber() {
        return accountNumber;
    }

    public Account setAccountNumber(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public Account setCustomerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account setBalance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }

    public Account setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
        return this;
    }

    public LocalDate getDateClosed() {
        return dateClosed;
    }

    public Account setDateClosed(LocalDate dateClosed) {
        this.dateClosed = dateClosed;
        return this;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public Account setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Account setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Account setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
