package com.ecommerce.order_api.model;
import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "Order_Item")

public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_seq_id")
    private Integer orderItemSeqId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", nullable = false)
    private String status;

    // Relationship: Many Items belong to One Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore // Prevents recursion

    private OrderHeader orderHeader;

    // Relationship: Many Items can refer to One Product
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
