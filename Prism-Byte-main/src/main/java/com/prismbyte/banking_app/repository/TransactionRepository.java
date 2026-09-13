package com.prismbyte.banking_app.repository;

import com.prismbyte.banking_app.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findBySourceAccountAccountNumberOrTargetAccountAccountNumberOrderByCreatedAtDesc(
            String sourceAccountNumber,
            String targetAccountNumber,
            Pageable pageable
    );

    Page<Transaction> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
