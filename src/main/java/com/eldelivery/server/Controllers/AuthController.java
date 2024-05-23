package com.eldelivery.server.Controllers;

import com.eldelivery.server.Models.User.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AuthenticationResponse> getUser(@RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok(service.checkJWT(jwt));
    }

    @GetMapping("/all-users")
    public ResponseEntity getAllUsers(@RequestHeader("Authorization") String jwt){
        try {
            service.checkJWT(jwt);
            return ResponseEntity.ok(service.getAllUsers());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e);
        }
    }

    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(service.updateUser(jwt, request));
    }

    @PutMapping("/me/password")
    public ResponseEntity<UserUpdateResponse> updateUserPassword(@RequestHeader("Authorization") String jwt, @RequestBody UserPasswordUpdateRequest request){
        return ResponseEntity.ok(service.changeUserPassword(jwt, request));
    }

}
