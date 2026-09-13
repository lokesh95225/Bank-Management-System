package com.prismbyte.banking_app.repository;

import com.prismbyte.banking_app.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findAllByOwnerIdOrderByCreatedAtDesc(Long ownerId);
}
