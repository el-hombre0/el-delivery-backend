package com.eldelivery.server.Repositories;

import com.eldelivery.server.Models.Car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Car, Long> {
}
