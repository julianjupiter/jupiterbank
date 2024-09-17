package com.jupiterbank.customer.service;

import com.jupiterbank.customer.dto.CreateCustomerDto;
import com.jupiterbank.customer.dto.CustomerDto;
import com.jupiterbank.customer.dto.UpdateCustomerDto;
import com.jupiterbank.customer.exception.CustomerNotFoundException;
import com.jupiterbank.customer.exception.DataValidationException;
import com.jupiterbank.customer.mapper.CustomerMapper;
import com.jupiterbank.customer.repository.CustomerRepository;
import com.jupiterbank.customer.util.CustomerId;
import com.jupiterbank.customer.util.ValidatorUtil;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

/**
 * @author Julian Jupiter
 */
@Service
@Transactional
public class DefaultCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final Validator validator;
    private final MessageSource messageSource;

    public DefaultCustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, Validator validator, MessageSource messageSource) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    public List<CustomerDto> findAll() {
        return this.customerRepository.findAll().stream()
                .map(this.customerMapper::map)
                .toList();
    }

    @Override
    public Optional<CustomerDto> findByCustomerId(String customerId) {
        var cId = CustomerId.fromString(customerId);
        return this.customerRepository.findByCustomerId(cId)
                .map(this.customerMapper::map);
    }

    @Override
    public CustomerDto create(CreateCustomerDto createCustomerDto) {
        this.validateCustomer(createCustomerDto);

        var newCustomer = this.customerMapper.map(CustomerId.create(), createCustomerDto);
        var createdCustomer = this.customerRepository.save(newCustomer);

        return this.customerMapper.map(createdCustomer);
    }

    @Override
    public CustomerDto update(String customerId, UpdateCustomerDto updateCustomerDto) {
        var cId = CustomerId.fromString(customerId);
        this.validateCustomer(updateCustomerDto);
        var existingCustomer = this.customerRepository.findByCustomerId(cId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer " + cId + " not found"));
        var customerUpdate = this.customerMapper.map(existingCustomer, updateCustomerDto);
        var updatedCustomer = this.customerRepository.save(customerUpdate);

        return this.customerMapper.map(updatedCustomer);
    }

    private void validateCustomer(CreateCustomerDto createCustomerDto) {
        var result = ValidatorUtil.validate(this.validator, createCustomerDto, "createCustomerDto");
        if (result.hasErrors()) {
            var errors = ValidatorUtil.validationErrors(result, this.messageSource);
            throw new DataValidationException("Invalid data request", errors);
        }
    }

    private void validateCustomer(UpdateCustomerDto updateCustomerDto) {
        var result = ValidatorUtil.validate(this.validator, updateCustomerDto, "updateCustomerDto");
        if (result.hasErrors()) {
            var errors = ValidatorUtil.validationErrors(result, this.messageSource);
            throw new DataValidationException("Invalid data request", errors);
        }
    }
}
