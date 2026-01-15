package com.ecommerce.order_api.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
    private String role; // Optional: only needed for registration
}