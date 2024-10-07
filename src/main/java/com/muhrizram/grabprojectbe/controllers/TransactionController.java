package com.muhrizram.grabprojectbe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;
import com.muhrizram.grabprojectbe.DTOs.responses.BodyResponse;
import com.muhrizram.grabprojectbe.services.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/create")
    public ResponseEntity<BodyResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        try {
            return transactionService.createTransaction(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTransaction(@Valid @RequestBody TransactionRequest request) {
        try {
            return transactionService.updateTransaction(request);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getDataTransactionById(@PathVariable("id") String id) {
        try {
            return transactionService.getDataTransactionById(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @GetMapping("/orders/{paxId}")
    public ResponseEntity<?> getDataTransactionByPaxId(
            @PathVariable("paxId") String paxId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        try {
            return transactionService.getDataTransactionByPaxId(paxId, page, limit, search, sortBy, direction);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
