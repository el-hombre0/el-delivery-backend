package com.eldelivery.server.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eldelivery.server.Models.Order;
import com.eldelivery.server.Repositories.OrderRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import com.eldelivery.server.Exceptions.ResourceNotFoundException;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderRepo.findOrderById(orderId);
    }

    @PostMapping("/neworder")
    public Order postOrder(@RequestBody Order order) {
        return orderRepo.save(order);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails) {
        Optional<Order> optionalOrder = Optional.of(orderRepo.findOrderById(orderId));
        Order order = optionalOrder
                .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " doesn't exist"));
        order.setAddress(orderDetails.getAddress());
        order.setCarModel(orderDetails.getCarModel());
        order.setClientEmail(orderDetails.getClientEmail());
        order.setClientName(orderDetails.getClientName());
        order.setClientSurname(orderDetails.getClientSurname());
        order.setClientPhone(orderDetails.getClientPhone());
        order.setRequiredKiloWatts(orderDetails.getRequiredKiloWatts());
        order.setDistanceToClient(orderDetails.getDistanceToClient());
        order.setCost(orderDetails.getCost());
        order.setPaymentMethod(orderDetails.getPaymentMethod());

        Order updatedOrder = orderRepo.save(order);
        return ResponseEntity.ok(updatedOrder);
    }


    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Map> deleteOrder(@PathVariable Long orderId) {
        Optional<Order> optionalOrder = Optional.of(orderRepo.findOrderById(orderId));
        Order order = optionalOrder
                .orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " doesn't exist"));
        orderRepo.deleteById(orderId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted:", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
