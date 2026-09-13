package com.prismbyte.banking_app.service;

import com.prismbyte.banking_app.dto.account.AccountResponse;
import com.prismbyte.banking_app.dto.account.CreateAccountRequest;
import com.prismbyte.banking_app.entity.Account;
import com.prismbyte.banking_app.entity.User;
import com.prismbyte.banking_app.entity.enums.AccountStatus;
import com.prismbyte.banking_app.exception.BusinessException;
import com.prismbyte.banking_app.exception.ResourceNotFoundException;
import com.prismbyte.banking_app.exception.UnauthorizedOperationException;
import com.prismbyte.banking_app.repository.AccountRepository;
import com.prismbyte.banking_app.repository.UserRepository;
import com.prismbyte.banking_app.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    @Transactional
    public AccountResponse createAccount(AppUserDetails userDetails, CreateAccountRequest request) {
        User user = getCurrentUser(userDetails);
        Account account = new Account();
        account.setOwner(user);
        account.setAccountName(request.accountName());
        account.setAccountType(request.accountType());
        account.setCurrency(request.currency() == null ? "USD" : request.currency().toUpperCase(Locale.ROOT));
        account.setAccountNumber(generateUniqueAccountNumber());
        Account savedAccount = accountRepository.save(account);
        log.info("Created {} account {} for user {}", savedAccount.getAccountType(), savedAccount.getAccountNumber(), user.getEmail());
        return toResponse(savedAccount);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getCurrentUserAccounts(AppUserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        return accountRepository.findAllByOwnerIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountByNumber(String accountNumber, AppUserDetails userDetails) {
        Account account = getAccountEntity(accountNumber);
        if (!ownsAccount(userDetails, account) && !hasBackOfficeAccess(userDetails)) {
            throw new UnauthorizedOperationException("You do not have access to this account");
        }
        return toResponse(account);
    }

    @Transactional
    public AccountResponse setFreezeStatus(String accountNumber, boolean frozen) {
        Account account = getAccountEntity(accountNumber);
        account.setStatus(frozen ? AccountStatus.FROZEN : AccountStatus.ACTIVE);
        log.info("Set freeze status {} for account {}", frozen, accountNumber);
        return toResponse(accountRepository.save(account));
    }

    @Transactional(readOnly = true)
    public Account getAccountEntity(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
    }

    public void assertAccountActive(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new BusinessException("Account is not active");
        }
    }

    public boolean ownsAccount(AppUserDetails userDetails, Account account) {
        return account.getOwner().getId().equals(userDetails.getId());
    }

    public boolean hasBackOfficeAccess(AppUserDetails userDetails) {
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                || userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }

    public AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getAccountName(),
                account.getAccountType().name(),
                account.getStatus().name(),
                account.getBalance(),
                account.getCurrency(),
                account.getOwner().getId(),
                account.getOwner().getFirstName() + " " + account.getOwner().getLastName(),
                account.getCreatedAt()
        );
    }

    private User getCurrentUser(AppUserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private String generateUniqueAccountNumber() {
        String candidate;
        do {
            long generated = 1_000_000_000L + Math.abs(secureRandom.nextLong() % 9_000_000_000L);
            candidate = String.valueOf(generated);
        } while (accountRepository.findByAccountNumber(candidate).isPresent());
        return candidate;
    }
}
