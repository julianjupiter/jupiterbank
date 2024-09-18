package com.jupiterbank.account.mapper;

import com.jupiterbank.account.dto.AccountDto;
import com.jupiterbank.account.dto.CreateAccountDto;
import com.jupiterbank.account.entity.Account;
import com.jupiterbank.account.entity.AccountStatus;
import com.jupiterbank.account.util.AccountNumber;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@Component
public class AccountMapper {
    public AccountDto map(Account account) {
        return new AccountDto(
                account.getAccountNumber(),
                account.getAccountType(),
                account.getCustomerId(),
                account.getBalance(),
                account.getDateOpened(),
                account.getDateClosed(),
                account.getAccountStatus(),
                account.getCreatedAt(),
                account.getUpdatedAt()
        );
    }

    public Account map(CreateAccountDto createAccountDto) {
        return new Account()
                .setAccountType(createAccountDto.accountType())
                .setAccountNumber(AccountNumber.create())
                .setCustomerId(createAccountDto.customerId())
                .setBalance(createAccountDto.amount())
                .setDateOpened(createAccountDto.dateOpened())
                .setAccountStatus(AccountStatus.ACTIVE)
                .setCreatedAt(Instant.now());
    }
}
