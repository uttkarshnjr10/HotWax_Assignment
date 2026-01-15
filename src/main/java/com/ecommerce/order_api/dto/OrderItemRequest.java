package com.ecommerce.order_api.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Integer productId;
    private Integer quantity;
    private String status;
}