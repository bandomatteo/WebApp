package com.bandomatteo.WebApp.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    @GetMapping("/hey")
    public ResponseEntity<String> sayHello(HttpServletRequest request) {
        System.out.println("sayHello method called");  // Verifica che il metodo venga chiamato
        System.out.println("Request Method: " + request.getMethod());
        System.out.println("Request URL: " + request.getRequestURL().toString());
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));

        return ResponseEntity.ok("Hello World!");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        System.out.println("testEndpoint method called");
        return ResponseEntity.ok("Test Successful");
    }
}
