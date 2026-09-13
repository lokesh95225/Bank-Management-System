package com.prismbyte.banking_app.controller;

import com.prismbyte.banking_app.dto.transaction.AmountRequest;
import com.prismbyte.banking_app.dto.transaction.TransactionResponse;
import com.prismbyte.banking_app.dto.transaction.TransferRequest;
import com.prismbyte.banking_app.security.AppUserDetails;
import com.prismbyte.banking_app.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit money into an account")
    public TransactionResponse deposit(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody AmountRequest request
    ) {
        return transactionService.deposit(userDetails, request);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw money from an account")
    public TransactionResponse withdraw(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody AmountRequest request
    ) {
        return transactionService.withdraw(userDetails, request);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money between accounts")
    public TransactionResponse transfer(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody TransferRequest request
    ) {
        return transactionService.transfer(userDetails, request);
    }

    @GetMapping("/account/{accountNumber}")
    @Operation(summary = "View transaction history for an account")
    public List<TransactionResponse> getAccountTransactions(
            @PathVariable String accountNumber,
            @AuthenticationPrincipal AppUserDetails userDetails,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return transactionService.getAccountTransactions(accountNumber, userDetails, page, size);
    }
}
