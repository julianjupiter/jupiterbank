package com.jupiterbank.transaction.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * @author Julian Jupiter
 */
public enum TransactionType {
    DEPOSIT, WITHDRAWAL;

    @JsonCreator
    public static TransactionType of(String type) {
        return Arrays.stream(values())
                .filter(transactionType -> transactionType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
