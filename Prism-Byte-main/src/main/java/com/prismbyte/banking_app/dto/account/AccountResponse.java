package com.prismbyte.banking_app.dto.account;

import java.math.BigDecimal;
import java.time.Instant;

public record AccountResponse(
        Long id,
        String accountNumber,
        String accountName,
        String accountType,
        String status,
        BigDecimal balance,
        String currency,
        Long ownerId,
        String ownerName,
        Instant createdAt
) {
}
