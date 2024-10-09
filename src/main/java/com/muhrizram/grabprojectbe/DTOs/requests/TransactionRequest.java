package com.muhrizram.grabprojectbe.DTOs.requests;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private String id;
    @NotEmpty
    private String paxId;
    @NotEmpty
    private String menuId;
    @NotEmpty
    private String status;
}
