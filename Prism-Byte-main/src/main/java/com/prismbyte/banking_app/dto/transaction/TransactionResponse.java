package com.prismbyte.banking_app.dto.transaction;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        String reference,
        String type,
        BigDecimal amount,
        BigDecimal balanceAfterTransaction,
        String sourceAccountNumber,
        String targetAccountNumber,
        String description,
        Long performedByUserId,
        Instant createdAt
) {
}
