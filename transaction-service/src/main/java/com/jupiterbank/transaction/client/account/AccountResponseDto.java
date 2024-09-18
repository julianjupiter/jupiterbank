package com.jupiterbank.transaction.client.account;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
public record AccountResponseDto(String title, int status, AccountDto data, URI path, Instant createdAt) {
    public static AccountResponseDto of(HttpStatus status, AccountDto data, URI path, Instant createdAt) {
        return new AccountResponseDto(status.getReasonPhrase(), status.value(), data, path, createdAt);
    }
}
