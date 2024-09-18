package com.jupiterbank.account.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * @author Julian Jupiter
 */
public record UpdateAccountDto(@NotNull(message = "${NotNull.updateAccountDto.balance}") BigDecimal balance) {
}
