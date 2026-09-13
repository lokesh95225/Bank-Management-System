package com.prismbyte.banking_app.dto.user;

import java.time.Instant;
import java.util.Set;

public record UserProfileResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        boolean enabled,
        boolean locked,
        Set<String> roles,
        Instant createdAt
) {
}
