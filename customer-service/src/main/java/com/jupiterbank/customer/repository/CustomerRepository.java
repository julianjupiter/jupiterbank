package com.jupiterbank.customer.repository;

import com.jupiterbank.customer.entity.Customer;
import com.jupiterbank.customer.util.CustomerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Julian Jupiter
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(CustomerId customerId);
}
