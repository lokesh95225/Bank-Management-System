package com.prismbyte.banking_app.controller;

import com.prismbyte.banking_app.dto.account.AccountResponse;
import com.prismbyte.banking_app.dto.admin.UserSummaryResponse;
import com.prismbyte.banking_app.dto.transaction.TransactionResponse;
import com.prismbyte.banking_app.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @Operation(summary = "View all users")
    public List<UserSummaryResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PatchMapping("/accounts/{accountNumber}/freeze")
    @Operation(summary = "Freeze a bank account")
    public AccountResponse freezeAccount(@PathVariable String accountNumber) {
        return adminService.freezeAccount(accountNumber);
    }

    @PatchMapping("/accounts/{accountNumber}/unfreeze")
    @Operation(summary = "Unfreeze a bank account")
    public AccountResponse unfreezeAccount(@PathVariable String accountNumber) {
        return adminService.unfreezeAccount(accountNumber);
    }

    @GetMapping("/transactions")
    @Operation(summary = "View all transactions")
    public List<TransactionResponse> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return adminService.getTransactions(page, size);
    }
}
