package com.eldelivery.server.Controllers.Order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.eldelivery.server.Models.Order.Order;
import com.eldelivery.server.Repositories.OrderRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.eldelivery.server.Exceptions.ResourceNotFoundException;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    private final OrderService service;


    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderRepo.findAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderRepo.findOrderById(orderId);
    }

    @GetMapping("/orders/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return orderRepo.findOrderByUserId(userId);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> postOrder(@RequestBody OrderCreationRequest request) {
        return ResponseEntity.ok(service.postOrder(request));
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
