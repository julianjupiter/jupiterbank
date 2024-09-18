package com.jupiterbank.transaction.entity;

import com.jupiterbank.transaction.util.AccountNumber;
import com.jupiterbank.transaction.util.TransactionId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Julian Jupiter
 */
@Converter
public class TransactionIdConverter implements AttributeConverter<TransactionId, String> {
    @Override
    public String convertToDatabaseColumn(TransactionId transactionId) {
        return transactionId != null ? transactionId.toString() : null;
    }

    @Override
    public TransactionId convertToEntityAttribute(String s) {
        return (s != null && !s.isBlank()) ? TransactionId.fromString(s) : null;
    }
}
