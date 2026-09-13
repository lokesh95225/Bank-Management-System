package com.prismbyte.banking_app.dto.auth;

import java.time.Instant;
import java.util.Set;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresIn,
        Instant issuedAt,
        Long userId,
        String email,
        Set<String> roles
) {
}
