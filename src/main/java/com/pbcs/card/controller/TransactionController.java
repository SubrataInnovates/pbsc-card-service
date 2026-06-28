package com.pbcs.card.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pbcs.card.dto.request.TransactionRequest;
import com.pbcs.card.dto.response.TransactionResponse;
import com.pbcs.card.service.TransactionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController 
{
	private final TransactionService transactionService;
	
	@PostMapping("/purchase")
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionResponse purchase(@Valid @RequestBody TransactionRequest request)
	{
		return transactionService.purchase(request);
	}
	 @PostMapping("/refund")
	 public TransactionResponse refund(@Valid @RequestBody TransactionRequest request) 
	 {

		 return transactionService.refund(request);
	 }

	 @GetMapping("/card/{cardId}")
	 public List<TransactionResponse> getTransactionsByCard(@PathVariable Long cardId)
	 {

		 return transactionService.getTransactionsByCard(cardId);
	 }
	

}
