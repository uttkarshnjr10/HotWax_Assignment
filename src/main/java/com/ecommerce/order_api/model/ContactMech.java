package com.ecommerce.order_api.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore; // To prevent infinite recursion in JSON

@Data
@Entity
@Table(name = "Contact_Mech")
public class ContactMech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_mech_id")
    private Integer contactMechId;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    // Relationship: Many Contact Mechs belong to One Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Foreign Key column
    @JsonIgnore // vital: prevents infinite loop when fetching Customer -> Contact -> Customer...
    private Customer customer;
}