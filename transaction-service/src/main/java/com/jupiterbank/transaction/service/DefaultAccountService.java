package com.jupiterbank.transaction.service;

import com.jupiterbank.transaction.client.account.AccountClient;
import com.jupiterbank.transaction.client.account.AccountDto;
import com.jupiterbank.transaction.client.account.AccountResponseDto;
import com.jupiterbank.transaction.client.account.UpdateBalanceDto;
import com.jupiterbank.transaction.util.AccountNumber;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    @Retry(name = "findByAccountNumberRetry")
    public Optional<AccountDto> findByAccountNumber(AccountNumber accountNumber) {
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
    @Retry(name = "updateBalanceRetry")
    public Optional<AccountDto> updateBalance(AccountNumber accountNumber, UpdateBalanceDto updateBalanceDto) {
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
