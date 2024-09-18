package com.jupiterbank.transaction.client.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PatchExchange;

/**
 * @author Julian Jupiter
 */
@HttpExchange(url = "/v1/accounts", accept = "application/json")
public interface AccountClient {
    @GetExchange("/{accountNumber}")
    ResponseEntity<AccountResponseDto> findByAccountNumber(@PathVariable String accountNumber);
    @PatchExchange("/{accountNumber}")
    ResponseEntity<AccountResponseDto> updateBalance(@PathVariable String accountNumber, @RequestBody UpdateBalanceDto updateBalanceDto);
}
