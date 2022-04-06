package com.natwest.transactionsender.web.controllers;

import java.util.Base64;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natwest.transactionsender.web.model.TransactionDTO;

@RestController
@RequestMapping("/api/v1")
public class TransactionSenderController {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${natwest.rcvr.apihost}")
	private String apihost;

	private static final String RECEIVER_PATH_V1 = "api/v1/receiveTransactionData/";

	@PostMapping("/sendTransactionData")
	public ResponseEntity<String> sendTransactionData(@RequestBody final TransactionDTO transactionDTO)
			throws JsonProcessingException {
		ObjectMapper ob = new ObjectMapper();
		byte[] transactionDTOString = ob.writeValueAsBytes(transactionDTO);
		restTemplate.postForLocation(apihost + RECEIVER_PATH_V1,
				Base64.getEncoder().encodeToString(transactionDTOString));
		return ResponseEntity.ok("Transaction sent successfully to the queue");
	}
}
