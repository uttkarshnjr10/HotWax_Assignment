package com.ecommerce.order_api.repository;

import com.ecommerce.order_api.model.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Integer> {
}