package com.jupiterbank.transaction.client.account;

import java.math.BigDecimal;

/**
 * @author Julian Jupiter
 */
public record UpdateBalanceDto(BigDecimal amount) {
}
