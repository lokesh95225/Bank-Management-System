package com.prismbyte.banking_app.controller;

import com.prismbyte.banking_app.dto.user.ProfileUpdateRequest;
import com.prismbyte.banking_app.dto.user.UserProfileResponse;
import com.prismbyte.banking_app.security.AppUserDetails;
import com.prismbyte.banking_app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get current user profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal AppUserDetails userDetails) {
        return userService.getCurrentUserProfile(userDetails);
    }

    @PutMapping
    @Operation(summary = "Update current user profile")
    public UserProfileResponse updateProfile(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody ProfileUpdateRequest request
    ) {
        return userService.updateCurrentUserProfile(userDetails, request);
    }
}
