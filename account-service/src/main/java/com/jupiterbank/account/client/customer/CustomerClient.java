package com.jupiterbank.account.client.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author Julian Jupiter
 */
@HttpExchange(url = "/v1/customers", accept = "application/json")
public interface CustomerClient {
    @GetExchange("/{customerId}")
    ResponseEntity<CustomerResponseDto> findById(@PathVariable String customerId);
}
