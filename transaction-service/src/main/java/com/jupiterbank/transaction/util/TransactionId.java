package com.jupiterbank.transaction.util;

import com.github.f4b6a3.uuid.UuidCreator;
import com.jupiterbank.transaction.exception.InvalidCustomerIdException;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public class TransactionId {
    private final UUID id;

    private TransactionId(UUID id) {
        this.id = id;
    }

    public static TransactionId create() {
        return new TransactionId(UuidCreator.getTimeOrderedEpoch());
    }

    //    @JsonCreator
    public static TransactionId fromString(String uuid) {
        try {
            return new TransactionId(UuidCreator.fromString(uuid));
        } catch (Exception exception) {
            throw new InvalidCustomerIdException("Invalid Transaction ID " + uuid);
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
