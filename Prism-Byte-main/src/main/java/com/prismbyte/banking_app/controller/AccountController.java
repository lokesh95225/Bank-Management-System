package com.prismbyte.banking_app.controller;

import com.prismbyte.banking_app.dto.account.AccountResponse;
import com.prismbyte.banking_app.dto.account.CreateAccountRequest;
import com.prismbyte.banking_app.security.AppUserDetails;
import com.prismbyte.banking_app.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new bank account")
    public AccountResponse createAccount(
            @AuthenticationPrincipal AppUserDetails userDetails,
            @Valid @RequestBody CreateAccountRequest request
    ) {
        return accountService.createAccount(userDetails, request);
    }

    @GetMapping("/my")
    @Operation(summary = "Get authenticated user's accounts")
    public List<AccountResponse> getMyAccounts(@AuthenticationPrincipal AppUserDetails userDetails) {
        return accountService.getCurrentUserAccounts(userDetails);
    }

    @GetMapping("/{accountNumber}")
    @Operation(summary = "Get account by account number")
    public AccountResponse getAccount(
            @PathVariable String accountNumber,
            @AuthenticationPrincipal AppUserDetails userDetails
    ) {
        return accountService.getAccountByNumber(accountNumber, userDetails);
    }
}
