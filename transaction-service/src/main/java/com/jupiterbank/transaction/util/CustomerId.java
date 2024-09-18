package com.jupiterbank.transaction.util;

import com.github.f4b6a3.uuid.UuidCreator;
import com.jupiterbank.transaction.exception.InvalidCustomerIdException;

import java.util.UUID;

/**
 * @author Julian Jupiter
 */
public class CustomerId {
    private final UUID id;

    private CustomerId(UUID id) {
        this.id = id;
    }

    public static CustomerId create() {
        return new CustomerId(UuidCreator.getTimeOrderedEpoch());
    }

//    @JsonCreator
    public static CustomerId fromString(String uuid) {
        try {
            return new CustomerId(UuidCreator.fromString(uuid));
        } catch (Exception exception) {
            throw new InvalidCustomerIdException("Invalid Customer ID " + uuid);
        }
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
