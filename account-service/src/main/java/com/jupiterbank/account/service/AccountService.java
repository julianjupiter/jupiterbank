package com.jupiterbank.account.service;

import com.jupiterbank.account.dto.AccountDto;
import com.jupiterbank.account.dto.CreateAccountDto;
import com.jupiterbank.account.dto.UpdateAccountDto;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface AccountService {
    List<AccountDto> findByCustomerId(CustomerId customerId);

    Optional<AccountDto> findByAccountNumber(AccountNumber accountNumber);

    AccountDto create(CreateAccountDto createAccountDto);

    AccountDto update(AccountNumber accountNumber, UpdateAccountDto updateAccountDto);

    void close(AccountNumber accountNumber, LocalDate dateClosed);

}
