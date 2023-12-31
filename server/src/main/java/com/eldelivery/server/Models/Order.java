package com.eldelivery.server.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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
    
}
