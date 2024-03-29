package com.eldelivery.server.Controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

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

    @PutMapping("/me")
    public ResponseEntity<UserUpdateResponse> updateUser(@RequestHeader("Authorization") String jwt, @RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(service.updateUser(jwt, request));
    }

    @PutMapping("/me/password")
    public ResponseEntity<UserUpdateResponse> updateUserPassword(@RequestHeader("Authorization") String jwt, @RequestBody UserPasswordUpdateRequest request){
        return ResponseEntity.ok(service.changeUserPassword(jwt, request));
    }

}
