package com.jupiterbank.transaction.client.account;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * @author Julian Jupiter
 */
public enum AccountType {
    CA, SA;

    @JsonCreator
    public static AccountType of(String type) {
        return Arrays.stream(values())
                .filter(accountType -> accountType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
