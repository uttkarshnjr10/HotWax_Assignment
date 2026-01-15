package com.ecommerce.order_api.controller;

import com.ecommerce.order_api.common.ApiResponse;
import com.ecommerce.order_api.dto.OrderItemRequest;
import com.ecommerce.order_api.dto.OrderRequest;
import com.ecommerce.order_api.model.OrderHeader;
import com.ecommerce.order_api.model.OrderItem;
import com.ecommerce.order_api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 1. Create Order
    @PostMapping
    public ResponseEntity<ApiResponse<OrderHeader>> createOrder(@RequestBody OrderRequest request) {
        OrderHeader order = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED.value(), order, "Order created successfully"));
    }

    // 2. Get Order (Scenario 2)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderHeader>> getOrder(@PathVariable Integer id) {
        OrderHeader order = orderService.getOrder(id);
        return ResponseEntity.ok(new ApiResponse<>(200, order, "Order retrieved successfully"));
    }

    // 3. Update Order Address
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderHeader>> updateOrder(@PathVariable Integer id, @RequestBody OrderRequest request) {
        OrderHeader order = orderService.updateOrder(id, request);
        return ResponseEntity.ok(new ApiResponse<>(200, order, "Order updated successfully"));
    }

    // 4. Delete Order (Scenario 6)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Order deleted successfully"));
    }

    // 5. Add Item (Scenario 4)
    @PostMapping("/{id}/items")
    public ResponseEntity<ApiResponse<OrderHeader>> addOrderItem(@PathVariable Integer id, @RequestBody OrderItemRequest request) {
        OrderHeader order = orderService.addOrderItem(id, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(201, order, "Item added to order"));
    }

    // 6. Update Item (Scenario 3)
    @PutMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse<OrderItem>> updateOrderItem(@PathVariable Integer id, @PathVariable Integer itemId, @RequestBody OrderItemRequest request) {
        OrderItem item = orderService.updateOrderItem(id, itemId, request);
        return ResponseEntity.ok(new ApiResponse<>(200, item, "Item updated successfully"));
    }

    // 7. Delete Item (Scenario 5)
    @DeleteMapping("/{id}/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrderItem(@PathVariable Integer id, @PathVariable Integer itemId) {
        orderService.deleteOrderItem(id, itemId);
        return ResponseEntity.ok(new ApiResponse<>(200, null, "Item deleted successfully"));
    }
}