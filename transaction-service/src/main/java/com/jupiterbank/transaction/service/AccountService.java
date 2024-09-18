package com.jupiterbank.transaction.service;

import com.jupiterbank.transaction.client.account.AccountDto;
import com.jupiterbank.transaction.client.account.UpdateBalanceDto;
import com.jupiterbank.transaction.util.AccountNumber;

import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface AccountService {
    Optional<AccountDto> findByAccountNumber(AccountNumber accountNumber);

    Optional<AccountDto> updateBalance(AccountNumber accountNumber, UpdateBalanceDto updateBalanceDto);
}
