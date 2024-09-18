package com.jupiterbank.account.controller;

import com.jupiterbank.account.dto.CreateAccountDto;
import com.jupiterbank.account.dto.DataResponseDto;
import com.jupiterbank.account.dto.UpdateAccountDto;
import com.jupiterbank.account.exception.AccountNotFoundException;
import com.jupiterbank.account.service.AccountService;
import com.jupiterbank.account.util.AccountNumber;
import com.jupiterbank.account.util.CustomerId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDate;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public DataResponseDto findByCustomerId(HttpServletRequest request, @RequestParam(name = "customer-id") String customerId) {
        var cId = CustomerId.fromString(customerId);
        var accounts = this.accountService.findByCustomerId(cId);

        return DataResponseDto.of(HttpStatus.OK, accounts, URI.create(request.getRequestURI()), Instant.now());
    }

    @GetMapping("/{accountNumber}")
    public DataResponseDto findByAccountNumber(HttpServletRequest request, @PathVariable String accountNumber) {
        var aNumber = AccountNumber.fromString(accountNumber);

        return this.accountService.findByAccountNumber(aNumber)
                .map(accountDto -> DataResponseDto.of(
                        HttpStatus.OK,
                        accountDto,
                        URI.create(request.getRequestURI()),
                        Instant.now()
                ))
                .orElseThrow(() -> new AccountNotFoundException("Account num ber " + accountNumber + " not found"));
    }

    @PostMapping
    public DataResponseDto create(HttpServletRequest request, @RequestBody CreateAccountDto createAccountDto) {
        var createAccount = this.accountService.create(createAccountDto);

        return DataResponseDto.of(
                HttpStatus.OK,
                createAccount,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @PatchMapping("/{accountNumber}")
    public DataResponseDto update(HttpServletRequest request, @PathVariable String accountNumber, @RequestBody UpdateAccountDto updateAccountDto) {
        var aNumber = AccountNumber.fromString(accountNumber);
        var updatedAccount = this.accountService.update(aNumber, updateAccountDto);

        return DataResponseDto.of(
                HttpStatus.OK,
                updatedAccount,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @PatchMapping("/{accountNumber}/close")
    public void close(@PathVariable String accountNumber, @RequestParam(name = "date-closed") String dateClosed) {
        var aNumber = AccountNumber.fromString(accountNumber);
        var dClosed = LocalDate.parse(dateClosed);
        this.accountService.close(aNumber, dClosed);
    }
}
