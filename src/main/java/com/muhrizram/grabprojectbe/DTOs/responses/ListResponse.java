package com.muhrizram.grabprojectbe.DTOs.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListResponse {
    private String message;
    private Integer statusCode;
    private String status;
    private Object data;
    private Long totalData;
    private Integer page;
    private Integer totalPage;
    private Integer limit;
}
