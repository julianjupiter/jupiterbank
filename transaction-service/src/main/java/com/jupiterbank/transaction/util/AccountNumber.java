package com.jupiterbank.transaction.util;

import com.github.f4b6a3.uuid.UuidCreator;
import com.jupiterbank.transaction.exception.InvalidAccountIdException;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public class AccountNumber {
    private final UUID id;

    private AccountNumber(UUID id) {
        this.id = id;
    }

    public static AccountNumber create() {
        return new AccountNumber(UuidCreator.getTimeOrderedEpoch());
    }

    public static AccountNumber fromString(String uuid) {
        try {
            return new AccountNumber(UuidCreator.fromString(uuid));
        } catch (Exception exception) {
            throw new InvalidAccountIdException("Invalid Customer ID " + uuid);
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
