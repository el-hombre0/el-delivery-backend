package com.eldelivery.server.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eldelivery.server.Models.User.User;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
