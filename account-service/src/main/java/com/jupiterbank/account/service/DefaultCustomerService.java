package com.jupiterbank.account.service;

import com.jupiterbank.account.client.customer.CustomerClient;
import com.jupiterbank.account.client.customer.CustomerDto;
import com.jupiterbank.account.client.customer.CustomerResponseDto;
import com.jupiterbank.account.util.CustomerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

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
    @Retryable(
            retryFor = {ResourceAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public Optional<CustomerDto> findById(CustomerId customerId) {
        RetryContext retryContext = RetrySynchronizationManager.getContext();
        if (retryContext != null) {
            log.info("Retry: {}", (retryContext.getRetryCount() + 1));
        } else {
            log.warn("RetryContext is null");
        }

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
