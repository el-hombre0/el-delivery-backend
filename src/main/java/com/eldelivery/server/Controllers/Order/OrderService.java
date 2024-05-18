package com.eldelivery.server.Controllers.Order;

import com.eldelivery.server.Models.User.User;
import com.eldelivery.server.Repositories.OrderRepo;
import com.eldelivery.server.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.eldelivery.server.Models.Order.Order;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final UserRepository userRepo;
    public OrderResponse postOrder(OrderCreationRequest request){
        User user = userRepo.findById(request.getUserId()).orElseThrow();
        User executor = userRepo.findById(request.getExecutorId()).orElseThrow();
        Order order = Order.builder()
                .id(request.getId())
                .user(user)
                .clientName(request.getClientName())
                .clientSurname(request.getClientSurname())
                .clientPhone(request.getClientPhone())
                .clientEmail(request.getClientEmail())
                .carModel(request.getCarModel())
                .requiredKiloWatts(request.getRequiredKiloWatts())
                .distanceToClient(request.getDistanceToClient())
                .address(request.getAddress())
                .cost(request.getCost())
                .paymentMethod(request.getPaymentMethod())
                .status(request.getStatus())
                .executor(executor)
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
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
                .build();
    }
}
