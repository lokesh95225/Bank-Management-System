package com.prismbyte.banking_app.repository;

import com.prismbyte.banking_app.entity.Role;
import com.prismbyte.banking_app.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);
}
