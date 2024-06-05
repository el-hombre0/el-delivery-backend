package com.eldelivery.server.Controllers.Order;

import com.eldelivery.server.Models.Order.Status;
import com.eldelivery.server.Models.User.User;
import com.eldelivery.server.Repositories.OrderRepo;
import com.eldelivery.server.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.eldelivery.server.Models.Order.Order;

import java.time.LocalDateTime;

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
        Order order = Order.builder()
                .user(user)
                .clientName(request.getClientName())
                .clientSurname(request.getClientSurname())
                .clientPhone(request.getClientPhone())
                .clientEmail(request.getClientEmail())
                .carModel(request.getCarModel())
                .requiredKiloWatts(request.getRequiredKiloWatts())
                .distanceToClient(request.getDistanceToClient())
                .address(request.getAddress())
                .cost(orderCostCalculation(request.getRequiredKiloWatts(), request.getDistanceToClient()))
                .paymentMethod(request.getPaymentMethod())
                .status(Status.PROCESSING)
                .executor(null)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .dateTime(currentDateTime)
                .build();
        orderRepo.save(order);
        return OrderResponse.builder()
                .id(order.getId())
                .user(order.getUser())
                .clientName(order.getClientName())
                .clientSurname(order.getClientSurname())
                .clientPhone(order.getClientPhone())
                .clientEmail(order.getClientEmail())
                .carModel(order.getCarModel())
                .requiredKiloWatts(order.getRequiredKiloWatts())
                .distanceToClient(order.getDistanceToClient())
                .address(order.getAddress())
                .cost(order.getCost())
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .executor(order.getExecutor())
                .latitude(order.getLatitude())
                .longitude(order.getLongitude())
                .dateTime(order.getDateTime())
                .build();
    }
}
