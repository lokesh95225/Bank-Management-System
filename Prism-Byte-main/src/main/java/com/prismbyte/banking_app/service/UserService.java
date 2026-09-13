package com.prismbyte.banking_app.service;

import com.prismbyte.banking_app.dto.user.ProfileUpdateRequest;
import com.prismbyte.banking_app.dto.user.UserProfileResponse;
import com.prismbyte.banking_app.entity.User;
import com.prismbyte.banking_app.exception.ResourceNotFoundException;
import com.prismbyte.banking_app.repository.UserRepository;
import com.prismbyte.banking_app.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserProfileResponse getCurrentUserProfile(AppUserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return toResponse(user);
    }

    @Transactional
    public UserProfileResponse updateCurrentUserProfile(AppUserDetails userDetails, ProfileUpdateRequest request) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());
        user.setAddress(request.address());
        log.info("Updated profile for user {}", user.getEmail());
        return toResponse(userRepository.save(user));
    }

    public UserProfileResponse toResponse(User user) {
        return new UserProfileResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.isEnabled(),
                user.isLocked(),
                user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()),
                user.getCreatedAt()
        );
    }
}
