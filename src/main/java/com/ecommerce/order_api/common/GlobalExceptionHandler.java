package com.ecommerce.order_api.common; // Make sure this matches your folder name

import com.ecommerce.order_api.common.ApiResponse;
import com.ecommerce.order_api.common.ApiException; // Check this import path
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom business exceptions (e.g., "Product not found")
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<Object>> handleApiException(ApiException ex) {

        // FIX: Pass (Status, Message) to match ApiResponse constructor
        ApiResponse<Object> response = new ApiResponse<>(
                ex.getStatus(),
                ex.getMessage()
        );

        return ResponseEntity
                .status(ex.getStatus())
                .body(response);
    }

    // Handle unexpected system crashes
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex) {

        // FIX: Pass (500, Message)
        ApiResponse<Object> response = new ApiResponse<>(
                500,
                "Internal Server Error: " + ex.getMessage()
        );

        return ResponseEntity
                .status(500)
                .body(response);
    }
}