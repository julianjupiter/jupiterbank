package com.jupiterbank.account.service;

import com.jupiterbank.account.client.customer.CustomerDto;
import com.jupiterbank.account.util.CustomerId;

import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface CustomerService {
    Optional<CustomerDto> findById(CustomerId customerId);
}
