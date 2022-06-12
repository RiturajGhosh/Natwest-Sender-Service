package com.natwest.transactionsender.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO class for transaction
 * 
 * @author Rituraj
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

	@JsonProperty("Account Number")
	private String accountNumber;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("Amount")
	private Double amount;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("AccountForm")
	private String accountForm;

}
