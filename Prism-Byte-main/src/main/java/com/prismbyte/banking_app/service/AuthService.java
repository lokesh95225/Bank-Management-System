package com.prismbyte.banking_app.service;

import com.prismbyte.banking_app.dto.auth.AuthResponse;
import com.prismbyte.banking_app.dto.auth.LoginRequest;
import com.prismbyte.banking_app.dto.auth.RefreshTokenRequest;
import com.prismbyte.banking_app.dto.auth.RegisterRequest;
import com.prismbyte.banking_app.entity.Role;
import com.prismbyte.banking_app.entity.User;
import com.prismbyte.banking_app.entity.enums.UserRole;
import com.prismbyte.banking_app.exception.BusinessException;
import com.prismbyte.banking_app.repository.RoleRepository;
import com.prismbyte.banking_app.repository.UserRepository;
import com.prismbyte.banking_app.security.AppUserDetails;
import com.prismbyte.banking_app.security.CustomUserDetailsService;
import com.prismbyte.banking_app.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessException("Email is already registered");
        }

        Role customerRole = roleRepository.findByName(UserRole.CUSTOMER)
                .orElseThrow(() -> new BusinessException("Customer role is not configured"));

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());
        user.getRoles().add(customerRole);

        User savedUser = userRepository.save(user);
        log.info("Registered new customer with email {}", savedUser.getEmail());
        return buildAuthResponse(new AppUserDetails(savedUser));
    }

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email().toLowerCase(), request.password())
        );
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        log.info("User {} logged in successfully", userDetails.getUsername());
        return buildAuthResponse(userDetails);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String email = jwtService.extractUsername(request.refreshToken());
        AppUserDetails userDetails = (AppUserDetails) userDetailsService.loadUserByUsername(email);

        if (!jwtService.isTokenValid(request.refreshToken(), userDetails, "refresh")) {
            throw new BusinessException("Invalid refresh token");
        }

        return buildAuthResponse(userDetails);
    }

    private AuthResponse buildAuthResponse(AppUserDetails userDetails) {
        Instant issuedAt = Instant.now();
        return new AuthResponse(
                jwtService.generateAccessToken(userDetails),
                jwtService.generateRefreshToken(userDetails),
                "Bearer",
                jwtService.getAccessTokenExpiration() / 1000,
                issuedAt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .collect(Collectors.toSet())
        );
    }
}
