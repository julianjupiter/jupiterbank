package com.jupiterbank.account.entity;

import com.jupiterbank.account.util.AccountNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * @author Julian Jupiter
 */
@Converter
public class AccountIdConverter implements AttributeConverter<AccountNumber, String> {
    @Override
    public String convertToDatabaseColumn(AccountNumber accountNumber) {
        return accountNumber != null ? accountNumber.toString() : null;
    }

    @Override
    public AccountNumber convertToEntityAttribute(String s) {
        return (s != null && !s.isBlank()) ? AccountNumber.fromString(s) : null;
    }
}
