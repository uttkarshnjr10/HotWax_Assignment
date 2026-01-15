package com.ecommerce.order_api.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Order_Header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_contact_mech_id", nullable = false)
    private ContactMech shippingContact;

    @ManyToOne
    @JoinColumn(name = "billing_contact_mech_id", nullable = false)
    private ContactMech billingContact;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}