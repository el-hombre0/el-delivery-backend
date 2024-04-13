package com.eldelivery.server.Controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.eldelivery.server.Models.User.Role;
import com.eldelivery.server.Models.User.User;
import com.eldelivery.server.Repositories.UserRepository;
import com.eldelivery.server.Security.JwtService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public AuthenticationResponse checkJWT(String jwtString) {
        String jwt = jwtString.substring(7);
        var userEmail = jwtService.extractUsername(jwt);
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        return AuthenticationResponse.builder()
                .token(jwt)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserUpdateResponse updateUser(String jwtString, UserUpdateRequest request) {
        String jwt = jwtString.substring(7);
        var userEmail = jwtService.extractUsername(jwt);
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return UserUpdateResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserUpdateResponse changeUserPassword(String jwtString, UserPasswordUpdateRequest request) {
        String jwt = jwtString.substring(7);
        var userEmail = jwtService.extractUsername(jwt);
        var user = userRepository.findByEmail(userEmail).orElseThrow();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return UserUpdateResponse.builder()
                .token(jwtToken)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UsersResponse getAllUsers(){
        List<User> usersList = userRepository.findAll();
        return UsersResponse.builder().usersList(usersList).build();
    }
}
