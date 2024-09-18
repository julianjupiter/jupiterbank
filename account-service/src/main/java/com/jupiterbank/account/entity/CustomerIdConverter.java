package com.jupiterbank.account.entity;

import com.jupiterbank.account.util.CustomerId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Julian Jupiter
 */
@Converter
public class CustomerIdConverter implements AttributeConverter<CustomerId, String> {
    @Override
    public String convertToDatabaseColumn(CustomerId customerId) {
        return customerId != null ? customerId.toString() : null;
    }

    @Override
    public CustomerId convertToEntityAttribute(String s) {
        return (s != null && !s.isBlank()) ? CustomerId.fromString(s) : null;
    }
}
