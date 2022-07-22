package com.natwest.transactionsender.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.natwest.transactionsender.events.TransactionSenderEvent;
import com.natwest.transactionsender.web.exception.ErrorDetails;
import com.natwest.transactionsender.web.exception.ResourceNotFoundException;
import common.model.TransactionDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionSenderControllerTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ErrorDetails errorDetails;

    @Mock
    private ResourceNotFoundException resourceNotFoundException;

    @Mock
    private TransactionDTO transactionDTO;

    @Mock
    private TransactionSenderEvent transactionSenderEvent;

    @InjectMocks
    private TransactionSenderController transactionSenderController;

    @Test
    public void testSendTransactionData() throws JsonProcessingException, URISyntaxException {
        ResponseEntity<String> responseEntity = ResponseEntity.ok("Transaction sent successfully to the queue");
        Assertions.assertEquals(responseEntity, transactionSenderController.sendTransactionData(transactionDTO));
    }
}