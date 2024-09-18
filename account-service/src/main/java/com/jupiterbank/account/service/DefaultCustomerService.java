package com.jupiterbank.account.service;

import com.jupiterbank.account.client.customer.CustomerClient;
import com.jupiterbank.account.client.customer.CustomerDto;
import com.jupiterbank.account.client.customer.CustomerResponseDto;
import com.jupiterbank.account.util.CustomerId;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Service
class DefaultCustomerService implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(DefaultCustomerService.class);
    private final CustomerClient customerClient;

    DefaultCustomerService(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    @Override
    @Retry(name = "findCustomerRetry")
    public Optional<CustomerDto> findById(CustomerId customerId) {
        ResponseEntity<CustomerResponseDto> response = this.customerClient.findById(customerId.toString());
        var httpStatusCode = response.getStatusCode();
        if (httpStatusCode == HttpStatus.OK) {
            CustomerResponseDto body = response.getBody();
            if (body != null) {
                return Optional.ofNullable(body.data());
            }
        }

        return Optional.empty();
    }
}
