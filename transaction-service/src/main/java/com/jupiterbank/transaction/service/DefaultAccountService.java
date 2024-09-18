package com.jupiterbank.transaction.service;

import com.jupiterbank.transaction.client.account.AccountClient;
import com.jupiterbank.transaction.client.account.AccountDto;
import com.jupiterbank.transaction.client.account.AccountResponseDto;
import com.jupiterbank.transaction.client.account.UpdateBalanceDto;
import com.jupiterbank.transaction.util.AccountNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultAccountService implements AccountService {
    private static final Logger log = LoggerFactory.getLogger(DefaultAccountService.class);
    private final AccountClient accountClient;

    DefaultAccountService(AccountClient accountClient) {
        this.accountClient = accountClient;
    }

    @Override
    @Retryable(
            retryFor = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public Optional<AccountDto> findByAccountNumber(AccountNumber accountNumber) {
        RetryContext retryContext = RetrySynchronizationManager.getContext();
        if (retryContext != null) {
            log.info("Retry: {}", (retryContext.getRetryCount() + 1));
        } else {
            log.warn("RetryContext is null");
        }

        ResponseEntity<AccountResponseDto> response = this.accountClient.findByAccountNumber(accountNumber.toString());
        var httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            AccountResponseDto body = response.getBody();
            if (body != null) {
                return Optional.ofNullable(body.data());
            }
        }

        return Optional.empty();
    }

    @Override
    @Retryable(
            retryFor = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public Optional<AccountDto> updateBalance(AccountNumber accountNumber, UpdateBalanceDto updateBalanceDto) {
        RetryContext retryContext = RetrySynchronizationManager.getContext();
        if (retryContext != null) {
            log.info("Retry: {}", (retryContext.getRetryCount() + 1));
        } else {
            log.warn("RetryContext is null");
        }

        ResponseEntity<AccountResponseDto> response = this.accountClient.updateBalance(accountNumber.toString(), updateBalanceDto);
        var httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            AccountResponseDto body = response.getBody();
            if (body != null) {
                return Optional.ofNullable(body.data());
            }
        }

        return Optional.empty();
    }
}
