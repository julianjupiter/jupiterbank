package com.jupiterbank.account.service;

import com.jupiterbank.account.dto.AccountDto;
import com.jupiterbank.account.dto.CreateAccountDto;
import com.jupiterbank.account.dto.UpdateAccountDto;
import com.jupiterbank.account.entity.AccountStatus;
import com.jupiterbank.account.exception.AccountNotFoundException;
import com.jupiterbank.account.exception.DataValidationException;
import com.jupiterbank.account.mapper.AccountMapper;
import com.jupiterbank.account.repository.AccountRepository;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;
import com.jupiterbank.account.util.ValidatorUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Service
@Transactional
class DefaultAccountService implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Validator validator;
    private final MessageSource messageSource;

    DefaultAccountService(AccountRepository accountRepository, AccountMapper accountMapper, Validator validator, MessageSource messageSource) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    public List<AccountDto> findByCustomerId(CustomerId customerId) {
        return this.accountRepository.findByCustomerId(customerId).stream()
                .map(this.accountMapper::map)
                .toList();
    }

    @Override
    public Optional<AccountDto> findByAccountNumber(AccountNumber accountNumber) {
        return this.accountRepository.findByAccountNumber(accountNumber)
                .map(this.accountMapper::map);
    }

    @Override
    public AccountDto create(CreateAccountDto createAccountDto) {
        this.validateCreateAccountRequest(createAccountDto);
        var newAccount = this.accountMapper.map(createAccountDto);
        var createdAccount = this.accountRepository.save(newAccount);

        return this.accountMapper.map(createdAccount);
    }

    @Override
    public AccountDto update(AccountNumber accountNumber, UpdateAccountDto updateAccountDto) {
        var existingAccount = this.accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account number " +  accountNumber + " not found"));
        this.accountMapper.map(existingAccount, updateAccountDto);
        var updatedAccount = this.accountRepository.save(existingAccount);

        return this.accountMapper.map(updatedAccount);
    }

    @Override
    public void close(AccountNumber accountNumber, LocalDate dateClosed) {
        var account = this.accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account number " +  accountNumber + " not found"));
        account.setAccountStatus(AccountStatus.CLOSED)
                .setDateClosed(dateClosed);
        this.accountRepository.save(account);
    }

    private void validateCreateAccountRequest(CreateAccountDto createAccountDto) {
        var result = ValidatorUtil.validate(this.validator, createAccountDto, "createAccountDto");
        if (result.hasErrors()) {
            var errors = ValidatorUtil.validationErrors(result, this.messageSource);
            throw new DataValidationException("Invalid data request", errors);
        }
    }
}
