package com.muhrizram.grabprojectbe.DTOs.requests;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
