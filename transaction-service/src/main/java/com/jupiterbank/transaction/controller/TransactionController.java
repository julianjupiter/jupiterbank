package com.jupiterbank.transaction.controller;

import com.jupiterbank.transaction.dto.CreateTransactionDto;
import com.jupiterbank.transaction.dto.DataResponseDto;
import com.jupiterbank.transaction.dto.TransactionDto;
import com.jupiterbank.transaction.entity.TransactionType;
import com.jupiterbank.transaction.exception.TransactionNotFoundException;
import com.jupiterbank.transaction.service.TransactionService;
import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;
import java.util.List;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public DataResponseDto findAll(HttpServletRequest request,
                                   @RequestParam(name = "account-number", required = false) String accountNumber,
                                   @RequestParam(name = "transaction-type", required = false) TransactionType transactionType) {

        List<TransactionDto> transactionDtos;
        if (accountNumber != null && transactionType != null) {
            var aNumber = AccountNumber.fromString(accountNumber);
            transactionDtos = this.transactionService.findByAccountNumberAndTransactionType(aNumber, transactionType);
        } else if (accountNumber != null ) {
            var aNumber = AccountNumber.fromString(accountNumber);
            transactionDtos = this.transactionService.findByAccountNumber(aNumber);
        } else {
            transactionDtos = this.transactionService.findAll();
        }

        return DataResponseDto.of(
                HttpStatus.OK,
                transactionDtos,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseDto create(HttpServletRequest request, @RequestBody CreateTransactionDto createTransactionDto) {
        var transactionDto = this.transactionService.create(createTransactionDto);

        return DataResponseDto.of(
                HttpStatus.CREATED,
                transactionDto,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @GetMapping("/{transactionId}")
    public DataResponseDto findByTransactionId(HttpServletRequest request, @PathVariable String transactionId) {
        var tId = TransactionId.fromString(transactionId);

        return this.transactionService.findByTransactionId(tId)
                .map(transactionDto -> DataResponseDto.of(
                        HttpStatus.OK,
                        transactionDto,
                        URI.create(request.getRequestURI()),
                        Instant.now()
                ))
                .orElseThrow(() -> new TransactionNotFoundException("Transaction " + tId + " not found"));
    }
}
