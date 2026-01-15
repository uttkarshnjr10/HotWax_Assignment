package com.ecommerce.order_api.repository;

import com.ecommerce.order_api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    // JpaRepository<Entity, ID_Type>
    // We put Integer because our customer_id is an INT.
}
