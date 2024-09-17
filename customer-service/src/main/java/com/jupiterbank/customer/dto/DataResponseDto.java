package com.jupiterbank.customer.dto;

import org.springframework.http.HttpStatus;

import java.net.URI;
import java.time.Instant;

/**
 * @author Julian Jupiter
 */
public record DataResponseDto(String title, int status, Object data, URI path, Instant createdAt) {
    public static DataResponseDto of(HttpStatus status, Object data, URI path, Instant createdAt) {
        return new DataResponseDto(status.getReasonPhrase(), status.value(), data, path, createdAt);
    }
}
