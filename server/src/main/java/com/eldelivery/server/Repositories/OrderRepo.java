package com.eldelivery.server.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eldelivery.server.Models.Order;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{
    Order findOrderById(Long orderId);
}
