package com.jupiterbank.transaction.service;

import com.jupiterbank.transaction.client.account.AccountDto;
import com.jupiterbank.transaction.client.account.AccountStatus;
import com.jupiterbank.transaction.client.account.UpdateBalanceDto;
import com.jupiterbank.transaction.dto.CreateTransactionDto;
import com.jupiterbank.transaction.dto.TransactionDto;
import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.exception.AccountNotFoundException;
import com.jupiterbank.transaction.exception.DataValidationException;
import com.jupiterbank.transaction.exception.InvalidAccountStatusException;
import com.jupiterbank.transaction.exception.InvalidWithdrawalAmountException;
import com.jupiterbank.transaction.exception.TransactionException;
import com.jupiterbank.transaction.mapper.TransactionMapper;
import com.jupiterbank.transaction.repository.TransactionRepository;
import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;
import com.jupiterbank.transaction.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Service
@Transactional
class DefaultTransactionService implements TransactionService {
    private static final Logger log = LoggerFactory.getLogger(DefaultTransactionService.class);
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final Validator validator;
    private final MessageSource messageSource;
    private final AccountService accountService;

    DefaultTransactionService(TransactionRepository transactionRepository,
                              TransactionMapper transactionMapper,
                              Validator validator,
                              MessageSource messageSource,
                              AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.validator = validator;
        this.messageSource = messageSource;
        this.accountService = accountService;
    }

    @Override
    public Optional<TransactionDto> findByTransactionId(TransactionId transactionId) {
        return this.transactionRepository.findByTransactionId(transactionId)
                .map(this.transactionMapper::map);
    }

    @Override
    public List<TransactionDto> findByAccountNumber(AccountNumber accountNumber) {
        return this.transactionRepository.findByAccountNumber(accountNumber).stream()
                .map(this.transactionMapper::map)
                .toList();
    }

    @Override
    public List<TransactionDto> findAll() {
        return this.transactionRepository.findAll().stream()
                .map(this.transactionMapper::map)
                .toList();
    }

    @Override
    public List<TransactionDto> findByAccountNumberAndTransactionType(AccountNumber accountNumber, TransactionType transactionType) {
        return this.transactionRepository.findByAccountNumberAndTransactionType(accountNumber, transactionType).stream()
                .map(this.transactionMapper::map)
                .toList();
    }

    @Override
    public TransactionDto create(CreateTransactionDto createTransactionDto) {
        this.validateCreateTransactionRequest(createTransactionDto);

        var accountNumber = createTransactionDto.accountNumber();
        // Validate if account exists
        var accountDto = this.getAccount(accountNumber);
        // Validate if account is active
        this.validateAccountStatus(accountDto);
        // Calculate balance
        var newBalance = this.calculateBalance(accountDto, createTransactionDto);

        // Save transaction
        var newTransaction = this.transactionMapper.map(createTransactionDto);
        var createdTransaction = this.transactionRepository.save(newTransaction);

        // Update account balance
        var updateBalanceDto = new UpdateBalanceDto(newBalance);
        var updatedAccount = this.updateAccountBalance(accountNumber, updateBalanceDto);

        return this.transactionMapper.map(createdTransaction, updatedAccount.balance());
    }

    private void validateCreateTransactionRequest(CreateTransactionDto createTransactionDto) {
        var result = ValidatorUtil.validate(this.validator, createTransactionDto, "createTransactionDto");
        if (result.hasErrors()) {
            var errors = ValidatorUtil.validationErrors(result, this.messageSource);
            throw new DataValidationException("Invalid data request", errors);
        }
    }

    private void validateAccountStatus(AccountDto accountDto) {
        if (accountDto.accountStatus() != AccountStatus.ACTIVE) {
            throw new InvalidAccountStatusException("Account is not active");
        }
    }

    private BigDecimal calculateBalance(AccountDto accountDto, CreateTransactionDto createTransactionDto) {
        var transactionType = createTransactionDto.transactionType();
        var balance = accountDto.balance();
        var amount = createTransactionDto.amount();

        return switch (transactionType) {
            case WITHDRAWAL -> {
                if (amount.compareTo(balance) > 0) {
                    throw new InvalidWithdrawalAmountException("Amount to withdraw is greater than account balance");
                }
                yield balance.subtract(amount)
                        .setScale(2, RoundingMode.HALF_UP);
            }
            case DEPOSIT -> balance.add(amount)
                    .setScale(2, RoundingMode.HALF_UP);
        };
    }

    private AccountDto getAccount(AccountNumber accountNumber) {
        var logMessage = "Error on request for account: {}";
        try {
            return this.accountService.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new TransactionException("Error on request for account", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (HttpClientErrorException e) {
            if (log.isDebugEnabled()) {
                log.debug(logMessage, e.getMessage());
            }

            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new AccountNotFoundException("Account number " + accountNumber + " not found");
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(logMessage, e.getMessage());
            }
        }

        throw new TransactionException("Error on request for account", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private AccountDto updateAccountBalance(AccountNumber accountNumber, UpdateBalanceDto updateBalanceDto) {
        var logMessage = "Error on request for updated account: {}";
        try {
            return this.accountService.updateBalance(accountNumber, updateBalanceDto)
                    .orElseThrow(() -> new TransactionException("Error on update of account balance", HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug(logMessage, e.getMessage());
            }

            throw new TransactionException("Error on request for updated account", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
