package com.ecommerce.order_api.common;

public class ApiException extends RuntimeException {

    private final int status; // HTTP status code (400, 404, etc.)

    public ApiException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
