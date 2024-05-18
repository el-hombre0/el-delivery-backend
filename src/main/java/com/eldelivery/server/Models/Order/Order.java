package com.eldelivery.server.Models.Order;

import com.eldelivery.server.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String clientName;
    private String clientSurname;
    private String clientPhone;
    private String clientEmail;
    private String carModel;
    private double requiredKiloWatts;
    private double distanceToClient;
    private String address;
    private double cost;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "executor_id", referencedColumnName = "id")
    private User executor;

    private double latitude;
    private double longitude;

    private LocalDateTime dateTime;
}
