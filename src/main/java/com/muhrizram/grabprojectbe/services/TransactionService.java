package com.muhrizram.grabprojectbe.services;

import org.springframework.http.ResponseEntity;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;
import com.muhrizram.grabprojectbe.DTOs.responses.BodyResponse;
import com.muhrizram.grabprojectbe.DTOs.responses.ListResponse;

public interface TransactionService {
    ResponseEntity<BodyResponse> createTransaction(TransactionRequest request);

    ResponseEntity<BodyResponse> updateTransaction(TransactionRequest request);

    ResponseEntity<BodyResponse> getDataTransactionById(String id);

    ResponseEntity<ListResponse> getDataTransactionByPaxId(String paxId, int page, int limit, String search,
            String sortBy, String direction);
}
