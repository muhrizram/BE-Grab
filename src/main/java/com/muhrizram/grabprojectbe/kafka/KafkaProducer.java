package com.muhrizram.grabprojectbe.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.muhrizram.grabprojectbe.DTOs.requests.TransactionRequest;;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, TransactionRequest> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, TransactionRequest> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderData(TransactionRequest request) {
        kafkaTemplate.send("order-input", request.getId(), request);
        System.out.println("Data Order terkirim ke Kafka: " + request);
    }
}