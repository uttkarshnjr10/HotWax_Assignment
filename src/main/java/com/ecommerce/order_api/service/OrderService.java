package com.ecommerce.order_api.service;

import com.ecommerce.order_api.common.ApiException;
import com.ecommerce.order_api.dto.OrderItemRequest;
import com.ecommerce.order_api.dto.OrderRequest;
import com.ecommerce.order_api.model.*;
import com.ecommerce.order_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ContactMechRepository contactMechRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    // SCENARIO 1: Create Order
    @Transactional
    public OrderHeader createOrder(OrderRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new ApiException(404, "Customer not found"));

        ContactMech shippingAddress = contactMechRepository.findById(request.getShippingContactMechId())
                .orElseThrow(() -> new ApiException(404, "Shipping Address not found"));

        ContactMech billingAddress = contactMechRepository.findById(request.getBillingContactMechId())
                .orElseThrow(() -> new ApiException(404, "Billing Address not found"));

        OrderHeader order = new OrderHeader();
        order.setCustomer(customer);
        order.setOrderDate(request.getOrderDate());
        order.setShippingContact(shippingAddress);
        order.setBillingContact(billingAddress);

        List<OrderItem> itemsToSave = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ApiException(404, "Product ID " + itemRequest.getProductId() + " not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setStatus(itemRequest.getStatus());
            orderItem.setOrderHeader(order);
            itemsToSave.add(orderItem);
        }
        order.setOrderItems(itemsToSave);
        return orderHeaderRepository.save(order);
    }

    // SCENARIO 2: Get Order Details
    public OrderHeader getOrder(Integer orderId) {
        return orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ApiException(404, "Order ID " + orderId + " not found"));
    }

    // SCENARIO 3 & 4 Support: Add Item to Order
    @Transactional
    public OrderHeader addOrderItem(Integer orderId, OrderItemRequest itemRequest) {
        OrderHeader order = getOrder(orderId); // Reuse getOrder to find parent

        Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ApiException(404, "Product ID " + itemRequest.getProductId() + " not found"));

        OrderItem newItem = new OrderItem();
        newItem.setProduct(product);
        newItem.setQuantity(itemRequest.getQuantity());
        newItem.setStatus(itemRequest.getStatus());
        newItem.setOrderHeader(order); // Link to parent

        orderItemRepository.save(newItem); // Save directly
        return orderHeaderRepository.findById(orderId).get(); // Return updated order
    }

    // SCENARIO 3 Support: Update Item
    @Transactional
    public OrderItem updateOrderItem(Integer orderId, Integer orderItemId, OrderItemRequest request) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ApiException(404, "Order Item ID " + orderItemId + " not found"));

        // Security check: Make sure this item actually belongs to the order!
        if (!item.getOrderHeader().getOrderId().equals(orderId)) {
            throw new ApiException(400, "Item does not belong to Order ID " + orderId);
        }

        if (request.getQuantity() != null) item.setQuantity(request.getQuantity());
        if (request.getStatus() != null) item.setStatus(request.getStatus());

        return orderItemRepository.save(item);
    }

    // SCENARIO 5: Delete Item
    @Transactional
    public void deleteOrderItem(Integer orderId, Integer orderItemId) {
        OrderItem item = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ApiException(404, "Order Item ID " + orderItemId + " not found"));

        if (!item.getOrderHeader().getOrderId().equals(orderId)) {
            throw new ApiException(400, "Item does not belong to Order ID " + orderId);
        }
        orderItemRepository.delete(item);
    }

    // SCENARIO 6: Delete Order
    @Transactional
    public void deleteOrder(Integer orderId) {
        OrderHeader order = getOrder(orderId);
        orderHeaderRepository.delete(order); // Cascading will delete items too
    }

    // Requirement: Update Order Address
    @Transactional
    public OrderHeader updateOrder(Integer orderId, OrderRequest request) {
        OrderHeader order = getOrder(orderId);

        if (request.getShippingContactMechId() != null) {
            ContactMech shipping = contactMechRepository.findById(request.getShippingContactMechId())
                    .orElseThrow(() -> new ApiException(404, "Shipping Address not found"));
            order.setShippingContact(shipping);
        }
        if (request.getBillingContactMechId() != null) {
            ContactMech billing = contactMechRepository.findById(request.getBillingContactMechId())
                    .orElseThrow(() -> new ApiException(404, "Billing Address not found"));
            order.setBillingContact(billing);
        }
        return orderHeaderRepository.save(order);
    }
}