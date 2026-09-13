package com.prismbyte.banking_app.bootstrap;

import com.prismbyte.banking_app.entity.Account;
import com.prismbyte.banking_app.entity.Role;
import com.prismbyte.banking_app.entity.User;
import com.prismbyte.banking_app.entity.enums.AccountType;
import com.prismbyte.banking_app.entity.enums.UserRole;
import com.prismbyte.banking_app.repository.AccountRepository;
import com.prismbyte.banking_app.repository.RoleRepository;
import com.prismbyte.banking_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.Locale;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${app.bootstrap-admin-email}")
    private String adminEmail;

    @Value("${app.bootstrap-admin-password}")
    private String adminPassword;

    @Value("${app.bootstrap-employee-email}")
    private String employeeEmail;

    @Value("${app.bootstrap-employee-password}")
    private String employeePassword;

    @Value("${app.bootstrap-customer-email}")
    private String customerEmail;

    @Value("${app.bootstrap-customer-password}")
    private String customerPassword;

    @Override
    public void run(String... args) {
        seedRoles();
        seedUser(adminEmail, adminPassword, "System", "Admin", UserRole.ADMIN);
        seedUser(employeeEmail, employeePassword, "Default", "Employee", UserRole.EMPLOYEE);
        User customer = seedUser(customerEmail, customerPassword, "Default", "Customer", UserRole.CUSTOMER);
        seedCustomerAccount(customer);
    }

    private void seedRoles() {
        ensureRole(UserRole.ADMIN, "Bank administrator with elevated privileges");
        ensureRole(UserRole.EMPLOYEE, "Bank operations employee");
        ensureRole(UserRole.CUSTOMER, "Retail banking customer");
    }

    private void ensureRole(UserRole role, String description) {
        roleRepository.findByName(role).orElseGet(() -> roleRepository.save(new Role(role, description)));
    }

    private User seedUser(String email, String password, String firstName, String lastName, UserRole roleName) {
        return userRepository.findByEmail(email.toLowerCase(Locale.ROOT)).orElseGet(() -> {
            Role role = roleRepository.findByName(roleName).orElseThrow();
            User user = new User();
            user.setEmail(email.toLowerCase(Locale.ROOT));
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEnabled(true);
            user.setLocked(false);
            user.setRoles(Set.of(role));
            User savedUser = userRepository.save(user);
            log.info("Seeded {} user {}", roleName, savedUser.getEmail());
            return savedUser;
        });
    }

    private void seedCustomerAccount(User customer) {
        if (!accountRepository.findAllByOwnerIdOrderByCreatedAtDesc(customer.getId()).isEmpty()) {
            return;
        }
        Account account = new Account();
        account.setOwner(customer);
        account.setAccountName(customer.getFirstName() + " Primary");
        account.setAccountType(AccountType.SAVINGS);
        account.setAccountNumber(generateAccountNumber());
        account.setCurrency("USD");
        account.setBalance(new BigDecimal("2500.00"));
        accountRepository.save(account);
        log.info("Seeded default customer account {}", account.getAccountNumber());
    }

    private String generateAccountNumber() {
        long generated = 1_000_000_000L + Math.abs(secureRandom.nextLong() % 9_000_000_000L);
        return String.valueOf(generated);
    }
}
