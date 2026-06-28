package com.pbcs.card.service;

import java.util.List;

import com.pbcs.card.dto.request.TransactionRequest;
import com.pbcs.card.dto.response.TransactionResponse;

public interface TransactionService 
{
	TransactionResponse purchase(TransactionRequest request);
	TransactionResponse refund(TransactionRequest request);
	List<TransactionResponse> getTransactionsByCard(Long cardId);

}
