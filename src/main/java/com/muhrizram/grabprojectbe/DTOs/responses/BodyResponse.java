package com.muhrizram.grabprojectbe.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BodyResponse {
    private String message;
    private int statusCode;
    private String status;
    private Object data;
}
