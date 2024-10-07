package com.muhrizram.grabprojectbe.kafka;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;
import com.muhrizram.grabprojectbe.models.olaps.OlapTransaction;
import com.muhrizram.grabprojectbe.models.oltps.OltpTransaction;
import com.muhrizram.grabprojectbe.repositories.olaps.OlapTransactionRepository;
import com.muhrizram.grabprojectbe.repositories.oltps.OltpTransactionRepository;

@Component
public class KafkaConsumer {

    @Autowired
    private OlapTransactionRepository olapTransactionRepository;

    @Autowired
    private OltpTransactionRepository oltpTransactionRepository;

    @KafkaListener(topics = "order-input", groupId = "grab-order")
    public void consumeOrderCreate(TransactionRequest request) {
        Optional<OltpTransaction> transaction = oltpTransactionRepository.findById(request.getId());
        if (transaction.isPresent()) {
            OlapTransaction order = new OlapTransaction();
            order.setId(request.getId());
            order.setUserId(transaction.get().getPaxId());
            order.setProductId(transaction.get().getProductId());
            order.setStatus(request.getStatus());
            order.setCreatedAt(transaction.get().getCreatedAt());
            order.setUpdatedAt(LocalDateTime.now());
            olapTransactionRepository.save(order);
            oltpTransactionRepository.delete(transaction.get());
        } else {
            throw new RuntimeException("Order not found with id: " + request.getId());
        }
    }

}
