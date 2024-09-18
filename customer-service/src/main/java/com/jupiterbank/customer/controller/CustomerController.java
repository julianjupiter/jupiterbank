package com.jupiterbank.customer.controller;

import com.jupiterbank.customer.dto.CreateCustomerDto;
import com.jupiterbank.customer.dto.DataResponseDto;
import com.jupiterbank.customer.dto.UpdateCustomerDto;
import com.jupiterbank.customer.exception.CustomerNotFoundException;
import com.jupiterbank.customer.service.CustomerService;
import com.jupiterbank.customer.util.CustomerId;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
@RestController
@RequestMapping("/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @RateLimiter(name = "findByAllCustomersRateLimiter")
    public DataResponseDto findAll(HttpServletRequest request) {
        var customers = this.customerService.findAll();

        return DataResponseDto.of(
                HttpStatus.OK,
                customers,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @GetMapping("/{customerId}")
    @RateLimiter(name = "findByCustomerIdRateLimiter")
    public DataResponseDto findById(HttpServletRequest request, @PathVariable String customerId) {
        var cId = CustomerId.fromString(customerId);

        return this.customerService.findByCustomerId(cId)
                .map(customerDto -> DataResponseDto.of(
                        HttpStatus.OK,
                        customerDto,
                        URI.create(request.getRequestURI()),
                        Instant.now()
                ))
                .orElseThrow(() -> new CustomerNotFoundException("Customer " + customerId + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataResponseDto create(HttpServletRequest request, @RequestBody CreateCustomerDto createCustomerDto) {
        var customer = this.customerService.create(createCustomerDto);

        return DataResponseDto.of(
                HttpStatus.CREATED,
                customer,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }

    @PatchMapping("/{customerId}")
    public DataResponseDto update(HttpServletRequest request, @PathVariable String customerId, @RequestBody UpdateCustomerDto updateCustomerDto) {
        var cId = CustomerId.fromString(customerId);

        var customer = this.customerService.update(cId, updateCustomerDto);

        return DataResponseDto.of(
                HttpStatus.OK,
                customer,
                URI.create(request.getRequestURI()),
                Instant.now()
        );
    }
}
