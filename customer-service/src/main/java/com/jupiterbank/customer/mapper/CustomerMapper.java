package com.jupiterbank.customer.mapper;

import com.jupiterbank.customer.dto.CreateCustomerDto;
import com.jupiterbank.customer.dto.CustomerDto;
import com.jupiterbank.customer.dto.UpdateCustomerDto;
import com.jupiterbank.customer.entity.Customer;
import com.jupiterbank.customer.util.CustomerId;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@Component
public class CustomerMapper {
    public CustomerDto map(Customer customer) {
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getLastName(),
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getExtensionName(),
                customer.getDateOfBirth(),
                customer.getContactNumber(),
                customer.getEmailAddress(),
                customer.getAddress(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public Customer map(CustomerId customerId, CreateCustomerDto createCustomerDto) {
        return new Customer()
                .setCustomerId(customerId)
                .setLastName(createCustomerDto.lastName())
                .setFirstName(createCustomerDto.firstName())
                .setMiddleName(createCustomerDto.middleName())
                .setExtensionName(createCustomerDto.extensionName())
                .setDateOfBirth(createCustomerDto.dateOfBirth())
                .setContactNumber(createCustomerDto.contactNumber())
                .setEmailAddress(createCustomerDto.emailAddress())
                .setAddress(createCustomerDto.address())
                .setCreatedAt(Instant.now());
    }

    public Customer map(Customer customer, UpdateCustomerDto updateCustomerDto) {
        return customer
                .setLastName(updateCustomerDto.lastName())
                .setFirstName(updateCustomerDto.firstName())
                .setMiddleName(updateCustomerDto.middleName())
                .setExtensionName(updateCustomerDto.extensionName())
                .setDateOfBirth(updateCustomerDto.dateOfBirth())
                .setContactNumber(updateCustomerDto.contactNumber())
                .setEmailAddress(updateCustomerDto.emailAddress())
                .setAddress(updateCustomerDto.address())
                .setUpdatedAt(Instant.now());
    }
}
