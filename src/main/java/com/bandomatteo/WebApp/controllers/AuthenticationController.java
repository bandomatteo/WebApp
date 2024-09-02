package com.bandomatteo.WebApp.controllers;

import com.bandomatteo.WebApp.domain.dto.AuthenticationRequestDTO;
import com.bandomatteo.WebApp.domain.dto.AuthenticationResponseDTO;
import com.bandomatteo.WebApp.domain.dto.RegisterRequestDTO;
import com.bandomatteo.WebApp.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/v1/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        //System.out.println(request);
        return ResponseEntity.ok(authenticationService.register(request));
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
