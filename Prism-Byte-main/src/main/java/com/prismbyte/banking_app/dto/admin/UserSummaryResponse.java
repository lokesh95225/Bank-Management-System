package com.prismbyte.banking_app.dto.admin;

import java.time.Instant;
import java.util.Set;

public record UserSummaryResponse(
        Long id,
        String fullName,
        String email,
        boolean enabled,
        boolean locked,
        Set<String> roles,
        Instant createdAt
) {
}
