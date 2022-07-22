package com.natwest.transactionsender.events;

import common.model.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionSenderEvent {
    @Autowired
    KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public void send(String topic,String key, TransactionDTO data) {
        kafkaTemplate.send(topic,key, data);
    }
}
