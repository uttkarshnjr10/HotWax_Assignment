package com.ecommerce.order_api.common;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private final int statusCode;
    private final T data;       // Data is 2nd argument
    private final String message; // Message is 3rd argument
    private final boolean success;
    private final LocalDateTime timestamp;

    // ✅ Constructor for SUCCESS (Matches your Controller)
    // Order: (int statusCode, T data, String message)
    public ApiResponse(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.success = statusCode < 400; // Auto-calculate success
        this.timestamp = LocalDateTime.now();
    }

    // ✅ Constructor for ERROR (Used by ExceptionHandler)
    // Order: (int statusCode, String message)
    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.data = null;
        this.message = message;
        this.success = false;
        this.timestamp = LocalDateTime.now();
    }
}