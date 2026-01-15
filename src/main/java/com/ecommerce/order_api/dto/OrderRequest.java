package com.ecommerce.order_api.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderRequest {
    private Integer customerId;
    private Date orderDate;
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
    private List<OrderItemRequest> orderItems; // Nested list of items
}