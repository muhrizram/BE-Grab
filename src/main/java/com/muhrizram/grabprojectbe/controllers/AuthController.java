package com.muhrizram.grabprojectbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import com.muhrizram.grabprojectbe.DTOs.requests.AuthRequest;
import com.muhrizram.grabprojectbe.DTOs.responses.AuthResponse;
import com.muhrizram.grabprojectbe.models.olaps.Pax;
import com.muhrizram.grabprojectbe.services.PaxService;
import com.muhrizram.grabprojectbe.utils.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PaxService paxService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Pax pax) {
        try {
            paxService.registerPax(pax.getFullName(), pax.getEmail(), pax.getUsername(), pax.getPassword());
            return ResponseEntity.ok("Passenger berhasil dibuat");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            final String jwt = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(jwt, authRequest.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Username atau password salah");
        }
    }
}
