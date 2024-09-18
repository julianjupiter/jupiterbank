package com.jupiterbank.account.client.customer;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
public record CustomerResponseDto(String title, int status, CustomerDto data, URI path, Instant createdAt) {
    public static CustomerResponseDto of(HttpStatus status, CustomerDto data, URI path, Instant createdAt) {
        return new CustomerResponseDto(status.getReasonPhrase(), status.value(), data, path, createdAt);
    }
}
