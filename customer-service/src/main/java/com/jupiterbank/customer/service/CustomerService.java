package com.jupiterbank.customer.service;

import com.jupiterbank.customer.dto.CreateCustomerDto;
import com.jupiterbank.customer.dto.CustomerDto;
import com.jupiterbank.customer.dto.UpdateCustomerDto;
import com.jupiterbank.customer.util.CustomerId;

import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface CustomerService {
    List<CustomerDto> findAll();

    Optional<CustomerDto> findByCustomerId(CustomerId customerId);

    CustomerDto create(CreateCustomerDto createCustomerDto);

    CustomerDto update(CustomerId customerId, UpdateCustomerDto updateCustomerDto);
}
