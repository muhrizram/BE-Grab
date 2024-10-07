package com.muhrizram.grabprojectbe.services;

import org.springframework.http.ResponseEntity;

import com.muhrizram.grabprojectbe.DTOs.responses.ListResponse;

public interface HistoryService {
    ResponseEntity<ListResponse> getDataHistoryOrders(int page, int limit, String search,
            String sortBy, String direction);
}
