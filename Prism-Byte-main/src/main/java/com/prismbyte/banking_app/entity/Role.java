package com.prismbyte.banking_app.entity;

import com.prismbyte.banking_app.entity.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AuditableEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 32)
    private UserRole name;

    @Column(nullable = false, length = 120)
    private String description;

    public Role(UserRole name, String description) {
        this.name = name;
        this.description = description;
    }
}
