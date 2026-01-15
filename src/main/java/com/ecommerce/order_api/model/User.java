package com.ecommerce.order_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users") // Don't use "User" as it's a reserved keyword in some DBs
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Will store BCrypt hash

    private String role; // e.g., "ROLE_USER", "ROLE_ADMIN"
}