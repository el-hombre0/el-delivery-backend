package com.eldelivery.server.Controllers.Order;

import com.eldelivery.server.Exceptions.ResourceNotFoundException;
import com.eldelivery.server.Models.Order.Status;
import com.eldelivery.server.Models.User.User;
import com.eldelivery.server.Repositories.OrderRepo;
import com.eldelivery.server.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.eldelivery.server.Models.Order.Order;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepository userRepo;

    public double orderCostCalculation(double requiredKiloWatts, double distanceToClient) {
        final double patrolConsumption = 14.5;
        final double kVtPerHourPrice = 6.43;
        final double carMaintenance = 183.33;
        final double extraCharge = 5;
        return Math.ceil((requiredKiloWatts * kVtPerHourPrice + distanceToClient * patrolConsumption + carMaintenance) * extraCharge);
    }

    public OrderResponse postOrder(OrderCreationRequest request) {
        User user = userRepo.findByEmail(request.getClientEmail()).orElseThrow();
//        User executor = userRepo.findById(request.getExecutorId()).orElseThrow();
        LocalDateTime currentDateTime = LocalDateTime.now();
        Order order = Order.builder().user(user).clientName(request.getClientName()).clientSurname(request.getClientSurname()).clientPhone(request.getClientPhone()).clientEmail(request.getClientEmail()).carModel(request.getCarModel()).requiredKiloWatts(request.getRequiredKiloWatts()).distanceToClient(request.getDistanceToClient()).address(request.getAddress()).cost(orderCostCalculation(request.getRequiredKiloWatts(), request.getDistanceToClient())).paymentMethod(request.getPaymentMethod()).status(Status.PROCESSING).executor(null).latitude(request.getLatitude()).longitude(request.getLongitude()).dateTime(currentDateTime).build();
        orderRepo.save(order);
        return OrderResponse.builder().id(order.getId()).user(order.getUser()).clientName(order.getClientName()).clientSurname(order.getClientSurname()).clientPhone(order.getClientPhone()).clientEmail(order.getClientEmail()).carModel(order.getCarModel()).requiredKiloWatts(order.getRequiredKiloWatts()).distanceToClient(order.getDistanceToClient()).address(order.getAddress()).cost(order.getCost()).paymentMethod(order.getPaymentMethod()).status(order.getStatus()).executor(order.getExecutor()).latitude(order.getLatitude()).longitude(order.getLongitude()).dateTime(order.getDateTime()).build();
    }

    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepo.findOrderById(orderId);
        return OrderResponse.builder().id(order.getId()).user(order.getUser()).clientName(order.getClientName()).clientSurname(order.getClientSurname()).clientPhone(order.getClientPhone()).clientEmail(order.getClientEmail()).carModel(order.getCarModel()).requiredKiloWatts(order.getRequiredKiloWatts()).distanceToClient(order.getDistanceToClient()).address(order.getAddress()).cost(order.getCost()).paymentMethod(order.getPaymentMethod()).status(order.getStatus()).executor(order.getExecutor()).latitude(order.getLatitude()).longitude(order.getLongitude()).dateTime(order.getDateTime()).build();
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepo.findOrderByUserId(userId);
        List<OrderResponse> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(OrderResponse.builder().id(order.getId()).user(order.getUser()).clientName(order.getClientName()).clientSurname(order.getClientSurname()).clientPhone(order.getClientPhone()).clientEmail(order.getClientEmail()).carModel(order.getCarModel()).requiredKiloWatts(order.getRequiredKiloWatts()).distanceToClient(order.getDistanceToClient()).address(order.getAddress()).cost(order.getCost()).paymentMethod(order.getPaymentMethod()).status(order.getStatus()).executor(order.getExecutor()).latitude(order.getLatitude()).longitude(order.getLongitude()).dateTime(order.getDateTime()).build());
        }
        return result;
    }

    public OrderResponse updateOrder(Long orderId, Order orderDetails) {
        Optional<Order> optionalOrder = Optional.of(orderRepo.findOrderById(orderId));
        Order order = optionalOrder.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " doesn't exist"));
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
        order.setStatus(orderDetails.getStatus());
        order.setExecutor(orderDetails.getExecutor());
        order.setLongitude(orderDetails.getLongitude());
        order.setLatitude(orderDetails.getLatitude());
        orderRepo.save(order);
        return OrderResponse.builder().id(order.getId()).user(order.getUser()).clientName(order.getClientName()).clientSurname(order.getClientSurname()).clientPhone(order.getClientPhone()).clientEmail(order.getClientEmail()).carModel(order.getCarModel()).requiredKiloWatts(order.getRequiredKiloWatts()).distanceToClient(order.getDistanceToClient()).address(order.getAddress()).cost(order.getCost()).paymentMethod(order.getPaymentMethod()).status(order.getStatus()).executor(order.getExecutor()).latitude(order.getLatitude()).longitude(order.getLongitude()).dateTime(order.getDateTime()).build();
    }

    // [Request processing failed: org.springframework.dao.DataIntegrityViolationException: could not execute statement [ERROR: update or delete on table "_user" violates foreign key constraint "fkgnfp6cq7wuhg8yec9j2d026m4" on table "orders"
    //  Подробности: Key (id)=(352) is still referenced from table "orders".] [delete from _user where id=?]; SQL [delete from _user where id=?]; constraint [fkgnfp6cq7wuhg8yec9j2d026m4]] with root cause
    public Map<String, Boolean> deleteOrder(Long orderId) {
        Optional<Order> optionalOrder = Optional.of(orderRepo.findOrderById(orderId));
        Order order = optionalOrder.orElseThrow(() -> new ResourceNotFoundException("Order with id: " + orderId + " doesn't exist"));
        orderRepo.deleteById(orderId);
        Map<String, Boolean> deletionResult = new HashMap<>();
        deletionResult.put("deleted:", Boolean.TRUE);
        return deletionResult;
    }
}
