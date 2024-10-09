package com.muhrizram.grabprojectbe.kafka;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;
import com.muhrizram.grabprojectbe.models.olaps.Menu;
import com.muhrizram.grabprojectbe.models.olaps.OlapTransaction;
import com.muhrizram.grabprojectbe.models.olaps.Pax;
import com.muhrizram.grabprojectbe.models.oltps.OltpTransaction;
import com.muhrizram.grabprojectbe.repositories.olaps.MenuRepository;
import com.muhrizram.grabprojectbe.repositories.olaps.OlapTransactionRepository;
import com.muhrizram.grabprojectbe.repositories.olaps.PaxRepository;
import com.muhrizram.grabprojectbe.repositories.oltps.OltpTransactionRepository;

@Component
public class KafkaConsumer {

    @Autowired
    private OlapTransactionRepository olapTransactionRepository;

    @Autowired
    private OltpTransactionRepository oltpTransactionRepository;

    @Autowired
    private PaxRepository paxRepository;

    @Autowired
    private MenuRepository menuRepository;

    @KafkaListener(topics = "order-input", groupId = "grab-order")
    public void consumeOrderCreate(TransactionRequest request) {
        Optional<OltpTransaction> transaction = oltpTransactionRepository.findById(request.getId());
        if (transaction.isPresent()) {
            Pax pax = paxRepository.findById(UUID.fromString(transaction.get().getPax().getId()))
                    .orElseThrow(() -> new RuntimeException("Pax not found"));
            Menu menu = menuRepository.findById(UUID.fromString(transaction.get().getMenu().getId()))
                    .orElseThrow(() -> new RuntimeException("Menu not found"));
            OlapTransaction order = new OlapTransaction();
            order.setId(request.getId());
            order.setPax(pax);
            order.setMenu(menu);
            order.setStatus(request.getStatus());
            order.setCreatedAt(transaction.get().getCreatedAt());
            order.setUpdatedAt(LocalDateTime.now());
            oltpTransactionRepository.delete(transaction.get());
            olapTransactionRepository.save(order);
        } else {
            throw new RuntimeException("Order not found with id: " + request.getId());
        }
    }

}
