package com.eldelivery.server.Controllers.Order;

import com.eldelivery.server.Models.Order.Status;
import com.eldelivery.server.Models.User.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {
    private Long id;
    private Integer userId;
    private String clientName;
    private String clientSurname;
    private String clientPhone;
    private String clientEmail;
    private String carModel;
    private double requiredKiloWatts;
    private double distanceToClient;
    private String address;
    private double cost;
    private String paymentMethod;
    private Status status;
    private Integer executorId;
    private double latitude;
    private double longitude;
}
