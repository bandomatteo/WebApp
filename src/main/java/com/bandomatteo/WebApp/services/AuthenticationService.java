package com.bandomatteo.WebApp.services;

import com.bandomatteo.WebApp.domain.dto.AuthenticationRequestDTO;
import com.bandomatteo.WebApp.domain.dto.AuthenticationResponseDTO;
import com.bandomatteo.WebApp.domain.dto.RegisterRequestDTO;
import com.bandomatteo.WebApp.domain.entities.User.Role;
import com.bandomatteo.WebApp.domain.entities.User.UserEntity;
import com.bandomatteo.WebApp.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private  JWTService jwtService;

    private AuthenticationManager authenticationManager;



    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponseDTO register(RegisterRequestDTO request) {

        UserEntity user = UserEntity
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

         var jwt = jwtService.generateToken(user);

         return AuthenticationResponseDTO
                 .builder()
                 .token(jwt)
                 .build();
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        System.out.println("Attempting to authenticate user with email: " + request.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("Authentication successful for email: " + request.getEmail());
        } catch (Exception e) {
            System.err.println("Authentication failed: " + e.getMessage());
            e.printStackTrace();  // Stampa lo stack trace completo per il debug
            throw new RuntimeException("Invalid credentials", e);
        }

        UserEntity user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponseDTO
                .builder()
                .token(jwt)
                .build();
    }

}
