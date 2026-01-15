package com.ecommerce.order_api.controller;

import com.ecommerce.order_api.common.ApiResponse;
import com.ecommerce.order_api.dto.AuthRequest;
import com.ecommerce.order_api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody AuthRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(new ApiResponse<>(201, result, "Success"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new ApiResponse<>(200, token, "Login Successful"));
    }
}