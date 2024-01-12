package com.eldelivery.server.Security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
        )
            throws ServletException, IOException {
        // throw new UnsupportedOperationException("Unimplemented method 'doFilterInternal'");
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Проверка JWT
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        // Получение JWT
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
    }

}
