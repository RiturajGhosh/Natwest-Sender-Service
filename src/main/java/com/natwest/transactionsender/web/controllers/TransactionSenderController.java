package com.natwest.transactionsender.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.transactionsender.events.TransactionSenderEvent;
import com.natwest.transactionsender.web.model.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1")
public class TransactionSenderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TransactionSenderEvent transactionSenderEvent;

    @Value("${natwest.rcvr.apihost}")
    private String apihost;

    @Value(value = "${topic.transaction.name}")
    private String transactionTopic;

    private static final String RECEIVER_PATH_V1 = "/api/v1/receiveTransactionData/";

    /**
     * This method will take a transaction dto as argument with the details for the transaction,
     * and it will encode it and send it to the receiver service
     *
     * @param transactionDTO
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping("/sendTransactionData")
    public ResponseEntity<String> sendTransactionData(@RequestBody final TransactionDTO transactionDTO)
            throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        byte[] transactionDTOString = ob.writeValueAsBytes(transactionDTO);
        // example for synchronous communication using rest template
        restTemplate.postForLocation(apihost + RECEIVER_PATH_V1,
                Base64.getEncoder().encodeToString(transactionDTOString));
        // example for producing data to kafka broker
        transactionSenderEvent.send(transactionTopic,transactionDTO.getAccountNumber(), transactionDTO);
        return ResponseEntity.ok("Transaction sent successfully to the queue");
    }
}
