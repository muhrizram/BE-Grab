package com.muhrizram.grabprojectbe.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String username;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }
}
