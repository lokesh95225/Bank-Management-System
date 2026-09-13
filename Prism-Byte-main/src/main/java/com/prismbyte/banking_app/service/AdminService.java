package com.prismbyte.banking_app.service;

import com.prismbyte.banking_app.dto.account.AccountResponse;
import com.prismbyte.banking_app.dto.admin.UserSummaryResponse;
import com.prismbyte.banking_app.dto.transaction.TransactionResponse;
import com.prismbyte.banking_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    @Transactional(readOnly = true)
    public List<UserSummaryResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserSummaryResponse(
                        user.getId(),
                        user.getFirstName() + " " + user.getLastName(),
                        user.getEmail(),
                        user.isEnabled(),
                        user.isLocked(),
                        user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()),
                        user.getCreatedAt()
                ))
                .toList();
    }

    @Transactional
    public AccountResponse freezeAccount(String accountNumber) {
        return accountService.setFreezeStatus(accountNumber, true);
    }

    @Transactional
    public AccountResponse unfreezeAccount(String accountNumber) {
        return accountService.setFreezeStatus(accountNumber, false);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactions(int page, int size) {
        return transactionService.getAllTransactions(page, size);
    }
}
