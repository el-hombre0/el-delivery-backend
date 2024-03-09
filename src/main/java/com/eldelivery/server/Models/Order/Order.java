package com.eldelivery.server.Models.Order;

import com.eldelivery.server.Models.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
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
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "MOBILE_GROUP_ID")
    private User executer;
    
}
