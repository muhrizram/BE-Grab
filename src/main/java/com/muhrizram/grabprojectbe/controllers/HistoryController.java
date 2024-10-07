package com.muhrizram.grabprojectbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muhrizram.grabprojectbe.services.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @GetMapping("/orders")
    public ResponseEntity<?> getDataTransactionByPaxId(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        try {
            return historyService.getDataHistoryOrders(page, limit, search, sortBy, direction);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
