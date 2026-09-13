package com.prismbyte.banking_app.service;

import com.prismbyte.banking_app.dto.transaction.AmountRequest;
import com.prismbyte.banking_app.dto.transaction.TransactionResponse;
import com.prismbyte.banking_app.dto.transaction.TransferRequest;
import com.prismbyte.banking_app.entity.Account;
import com.prismbyte.banking_app.entity.Transaction;
import com.prismbyte.banking_app.entity.User;
import com.prismbyte.banking_app.entity.enums.TransactionType;
import com.prismbyte.banking_app.exception.BusinessException;
import com.prismbyte.banking_app.exception.ResourceNotFoundException;
import com.prismbyte.banking_app.exception.UnauthorizedOperationException;
import com.prismbyte.banking_app.repository.AccountRepository;
import com.prismbyte.banking_app.repository.TransactionRepository;
import com.prismbyte.banking_app.repository.UserRepository;
import com.prismbyte.banking_app.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;

    @Transactional
    public TransactionResponse deposit(AppUserDetails userDetails, AmountRequest request) {
        Account account = accountService.getAccountEntity(request.accountNumber());
        authorizeAccountAccess(userDetails, account);
        accountService.assertAccountActive(account);
        account.setBalance(account.getBalance().add(request.amount()));
        accountRepository.save(account);
        Transaction transaction = saveTransaction(TransactionType.DEPOSIT, request.amount(), account.getBalance(),
                null, account, request.description(), getCurrentUser(userDetails));
        log.info("Deposited {} into account {}", request.amount(), account.getAccountNumber());
        return toResponse(transaction);
    }

    @Transactional
    public TransactionResponse withdraw(AppUserDetails userDetails, AmountRequest request) {
        Account account = accountService.getAccountEntity(request.accountNumber());
        authorizeAccountAccess(userDetails, account);
        accountService.assertAccountActive(account);
        validateSufficientBalance(account, request.amount());
        account.setBalance(account.getBalance().subtract(request.amount()));
        accountRepository.save(account);
        Transaction transaction = saveTransaction(TransactionType.WITHDRAWAL, request.amount(), account.getBalance(),
                account, null, request.description(), getCurrentUser(userDetails));
        log.info("Withdrew {} from account {}", request.amount(), account.getAccountNumber());
        return toResponse(transaction);
    }

    @Transactional
    public TransactionResponse transfer(AppUserDetails userDetails, TransferRequest request) {
        if (request.sourceAccountNumber().equals(request.targetAccountNumber())) {
            throw new BusinessException("Source and target accounts cannot be the same");
        }

        Account sourceAccount = accountService.getAccountEntity(request.sourceAccountNumber());
        Account targetAccount = accountService.getAccountEntity(request.targetAccountNumber());
        authorizeAccountAccess(userDetails, sourceAccount);
        accountService.assertAccountActive(sourceAccount);
        accountService.assertAccountActive(targetAccount);
        validateSufficientBalance(sourceAccount, request.amount());

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(request.amount()));
        targetAccount.setBalance(targetAccount.getBalance().add(request.amount()));
        accountRepository.save(sourceAccount);
        accountRepository.save(targetAccount);

        Transaction transaction = saveTransaction(
                TransactionType.TRANSFER,
                request.amount(),
                sourceAccount.getBalance(),
                sourceAccount,
                targetAccount,
                request.description(),
                getCurrentUser(userDetails)
        );
        log.info("Transferred {} from {} to {}", request.amount(), sourceAccount.getAccountNumber(), targetAccount.getAccountNumber());
        return toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAccountTransactions(String accountNumber, AppUserDetails userDetails, int page, int size) {
        Account account = accountService.getAccountEntity(accountNumber);
        authorizeAccountAccess(userDetails, account);
        return transactionRepository
                .findBySourceAccountAccountNumberOrTargetAccountAccountNumberOrderByCreatedAtDesc(
                        accountNumber,
                        accountNumber,
                        PageRequest.of(page, size)
                )
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getAllTransactions(int page, int size) {
        return transactionRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private Transaction saveTransaction(
            TransactionType type,
            BigDecimal amount,
            BigDecimal balanceAfterTransaction,
            Account sourceAccount,
            Account targetAccount,
            String description,
            User performedBy
    ) {
        Transaction transaction = new Transaction();
        transaction.setReference(UUID.randomUUID().toString());
        transaction.setType(type);
        transaction.setAmount(amount);
        transaction.setBalanceAfterTransaction(balanceAfterTransaction);
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
        transaction.setDescription(description);
        transaction.setPerformedBy(performedBy);
        return transactionRepository.save(transaction);
    }

    private void authorizeAccountAccess(AppUserDetails userDetails, Account account) {
        if (!accountService.ownsAccount(userDetails, account) && !accountService.hasBackOfficeAccess(userDetails)) {
            throw new UnauthorizedOperationException("You are not allowed to operate on this account");
        }
    }

    private void validateSufficientBalance(Account account, BigDecimal amount) {
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException("Insufficient account balance");
        }
    }

    private User getCurrentUser(AppUserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getReference(),
                transaction.getType().name(),
                transaction.getAmount(),
                transaction.getBalanceAfterTransaction(),
                transaction.getSourceAccount() != null ? transaction.getSourceAccount().getAccountNumber() : null,
                transaction.getTargetAccount() != null ? transaction.getTargetAccount().getAccountNumber() : null,
                transaction.getDescription(),
                transaction.getPerformedBy() != null ? transaction.getPerformedBy().getId() : null,
                transaction.getCreatedAt()
        );
    }
}
