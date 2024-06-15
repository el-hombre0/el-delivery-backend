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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderRepo orderRepo;

    private final OrderService service;

    /**
     * Получение всех заказов
     * @return
     */
    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        try {
            List<Order> orders = orderRepo.findAll();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Получение одного заказа
     * @param orderId
     * @return
     */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long orderId){
        return ResponseEntity.ok(service.getOrder(orderId));
    }

    /**
     * Получение всех заказов пользователя
     * @param userId
     * @return
     */
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getUserOrders(userId));
    }

    /**
     * Создание заказа
     * @param request
     * @return
     */
    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> postOrder(@RequestBody OrderCreationRequest request) {
        return ResponseEntity.ok(service.postOrder(request));
    }

    /**
     * Изменение заказа
     * @param orderId
     * @param orderDetails
     * @return
     */
    @PutMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long orderId, @RequestBody Order orderDetails) {
        return ResponseEntity.ok(service.updateOrder(orderId, orderDetails));
    }


        /**
         * Удаление заказа
         * @param orderId
         * @return
         */
    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Map<String, Boolean>> deleteOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(service.deleteOrder(orderId));
    }

}
