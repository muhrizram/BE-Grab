package com.muhrizram.grabprojectbe.DTOs.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TransactionRequest {
    private String id;
    @NotEmpty
    private String paxId;
    @NotEmpty
    private String status;
}
