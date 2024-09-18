package com.jupiterbank.account.service;

import com.jupiterbank.account.client.customer.CustomerDto;
import com.jupiterbank.account.dto.AccountDto;
import com.jupiterbank.account.dto.CreateAccountDto;
import com.jupiterbank.account.dto.UpdateAccountDto;
import com.jupiterbank.account.entity.AccountStatus;
import com.jupiterbank.account.exception.AccountException;
import com.jupiterbank.account.exception.AccountNotFoundException;
import com.jupiterbank.account.exception.CustomerNotFoundException;
import com.jupiterbank.account.exception.DataValidationException;
import com.jupiterbank.account.mapper.AccountMapper;
import com.jupiterbank.account.repository.AccountRepository;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;
import com.jupiterbank.account.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;

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
    private static final Logger log = LoggerFactory.getLogger(DefaultAccountService.class);
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Validator validator;
    private final MessageSource messageSource;
    private final CustomerService customerService;

    DefaultAccountService(AccountRepository accountRepository,
                          AccountMapper accountMapper,
                          Validator validator,
                          MessageSource messageSource,
                          CustomerService customerService) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.validator = validator;
        this.messageSource = messageSource;
        this.customerService = customerService;
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
        // If no exception, customer exists
        this.getCustomer(createAccountDto.customerId());

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

    private CustomerDto getCustomer(CustomerId customerId) {
        var logMessage = "Error on request for customer: {}";
        try {
            return this.customerService.findById(customerId)
                    .orElseThrow(() -> new AccountException("Error on request for customer", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (HttpClientErrorException e) {
            if (log.isDebugEnabled()) {
                log.debug(logMessage, e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CustomerNotFoundException("Customer ID " + customerId + " not found");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(logMessage, e.getMessage());
            }
        }

        throw new AccountException("Error on request for customer", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
