package com.muhrizram.grabprojectbe.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;
import com.muhrizram.grabprojectbe.DTOs.responses.BodyResponse;
import com.muhrizram.grabprojectbe.DTOs.responses.ListResponse;
import com.muhrizram.grabprojectbe.kafka.KafkaProducer;
import com.muhrizram.grabprojectbe.models.oltps.OltpTransaction;
import com.muhrizram.grabprojectbe.repositories.oltps.OltpTransactionRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    OltpTransactionRepository oltpTransactionRepository;

    @Autowired
    KafkaProducer kafkaProducer;

    public ResponseEntity<BodyResponse> createTransaction(TransactionRequest request) {
        String id = UUID.randomUUID().toString();
        String paxId = request.getPaxId();
        String productId = UUID.randomUUID().toString();
        LocalDateTime time = LocalDateTime.now();
        String status = request.getStatus();

        OltpTransaction oltpTransactions = new OltpTransaction(id, paxId, productId, status, time, time);
        oltpTransactionRepository.save(oltpTransactions);

        BodyResponse bodyResponse = BodyResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message("Successfully Create Transaction")
                .data(oltpTransactions)
                .build();

        return ResponseEntity.ok().body(bodyResponse);
    }

    public ResponseEntity<BodyResponse> updateTransaction(TransactionRequest request) {
        Optional<OltpTransaction> transactionOptional = oltpTransactionRepository.findById(request.getId());
        HttpStatus status;
        String message;
        OltpTransaction transaction = null;

        if (!transactionOptional.isPresent()) {
            status = HttpStatus.NOT_FOUND;
            message = "Transaction not found with id: " + request.getId();
        } else {
            transaction = transactionOptional.get();
            transaction.setStatus(request.getStatus());
            oltpTransactionRepository.save(transaction);

            kafkaProducer.sendOrderData(request);

            status = HttpStatus.OK;
            message = "Successfully Update Transaction";
        }

        BodyResponse bodyResponse = BodyResponse.builder()
                .statusCode(status.value())
                .status(status.name())
                .message(message)
                .data(transaction)
                .build();

        return ResponseEntity.status(status).body(bodyResponse);
    }

    public ResponseEntity<BodyResponse> getDataTransactionById(String id) {
        Optional<OltpTransaction> transactionOptional = oltpTransactionRepository.findById(id);
        HttpStatus status = transactionOptional.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = transactionOptional.isPresent() ? "Successfully Get Transaction" : "Transaction Not Found";
        OltpTransaction transaction = transactionOptional.orElse(null);

        BodyResponse bodyResponse = BodyResponse.builder()
                .statusCode(status.value())
                .status(status.name())
                .message(message)
                .data(transaction)
                .build();

        return ResponseEntity.status(status).body(bodyResponse);
    }

    public ResponseEntity<ListResponse> getDataTransactionByPaxId(String paxId, int page, int limit, String search,
            String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<OltpTransaction> transactionsPage;
        if (search != null && !search.isEmpty()) {
            transactionsPage = oltpTransactionRepository.findByPaxIdAndMultiFieldSearch(paxId, search, pageable);
        } else {
            transactionsPage = oltpTransactionRepository.findByPaxId(paxId, pageable);
        }

        HttpStatus status = transactionsPage.hasContent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        String message = transactionsPage.hasContent() ? "Successfully Get Transactions" : "Transactions Not Found";
        List<OltpTransaction> transactions = transactionsPage.getContent();
        Long totalData = transactionsPage.getTotalElements();
        Integer totalPage = transactionsPage.getTotalPages();

        ListResponse bodyResponse = ListResponse.builder()
                .statusCode(status.value())
                .status(status.name())
                .message(message)
                .data(transactions)
                .totalData(totalData)
                .page(page)
                .totalPage(totalPage)
                .limit(limit)
                .build();

        return ResponseEntity.status(status).body(bodyResponse);
    }
}
