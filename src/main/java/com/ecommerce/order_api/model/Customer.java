package com.ecommerce.order_api.model;

import jakarta.persistence.*;
import lombok.Data; // specific import for Lombok
import java.util.List;

@Data // Lombok automatically generates Getters, Setters, toString, etc.
@Entity
@Table(name = "Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment like MongoDB _id
    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    // Relationship: One Customer has many Contact Mechanisms
    // mappedBy refers to the "customer" field in ContactMech class
    // cascade = CascadeType.ALL means if you delete a customer, their contacts are deleted too
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ContactMech> contactMechs;
}
