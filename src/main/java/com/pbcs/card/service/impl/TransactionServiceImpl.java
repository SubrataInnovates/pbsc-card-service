package com.pbcs.card.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbcs.card.dto.request.TransactionRequest;
import com.pbcs.card.dto.response.TransactionResponse;
import com.pbcs.card.entity.Card;
import com.pbcs.card.entity.Transaction;
import com.pbcs.card.enums.CardStatus;
import com.pbcs.card.enums.TransactionStatus;
import com.pbcs.card.enums.TransactionType;
import com.pbcs.card.exception.CardNotFoundException;
import com.pbcs.card.exception.InsufficientBalanceException;
import com.pbcs.card.mapper.TransactionMapper;
import com.pbcs.card.repository.CardRepository;
import com.pbcs.card.repository.TransactionRepository;
import com.pbcs.card.service.TransactionService;
import com.pbcs.card.util.TransactionIdGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService
{
	private final CardRepository cardRepository;
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	
	@Override
	public TransactionResponse purchase(TransactionRequest request)
	{
		Card card = cardRepository.findById(request.getCardId()).orElseThrow(()->new CardNotFoundException("Card not found with id :"+request.getCardId()));
		
		if(card.getStatus()!=CardStatus.ACTIVE)
		{
			throw new IllegalStateException("Only active cards can perform purchase.");
		}
		if(card.getBalance().compareTo(request.getAmount())<0)
		{
			throw new InsufficientBalanceException("Insufficient balance.");
			
		}
		BigDecimal balanceBefore = card.getBalance();
		BigDecimal balanceAfter = balanceBefore.subtract(request.getAmount());
		
		card.setBalance(balanceAfter);
		
		Transaction transaction=new Transaction();
		transaction.setTransactionId(TransactionIdGenerator.generate());
		transaction.setCard(card);
		transaction.setTransactionType(TransactionType.PURCHASE);
		transaction.setAmount(request.getAmount());
		transaction.setBalanceBefore(balanceBefore);
		transaction.setBalanceAfter(balanceAfter);
		transaction.setTransactionStatus(TransactionStatus.SUCCESS);
		transaction.setRemarks(request.getRemarks());
		
		cardRepository.save(card);
		
		Transaction savedTransaction = transactionRepository.save(transaction);

	    return transactionMapper.toResponse(savedTransaction);
	}

	@Override
	public TransactionResponse refund(TransactionRequest request) {

	    Card card = cardRepository.findById(request.getCardId())
	            .orElseThrow(() ->
	                    new CardNotFoundException("Card not found with id: " + request.getCardId()));

	    if (card.getStatus() != CardStatus.ACTIVE) {
	        throw new IllegalStateException("Only active cards can receive refund.");
	    }

	    BigDecimal balanceBefore = card.getBalance();
	    BigDecimal balanceAfter = balanceBefore.add(request.getAmount());

	    card.setBalance(balanceAfter);

	    Transaction transaction = new Transaction();
	    transaction.setTransactionId(TransactionIdGenerator.generate());
	    transaction.setCard(card);
	    transaction.setTransactionType(TransactionType.REFUND);
	    transaction.setAmount(request.getAmount());
	    transaction.setBalanceBefore(balanceBefore);
	    transaction.setBalanceAfter(balanceAfter);
	    transaction.setTransactionStatus(TransactionStatus.SUCCESS);
	    transaction.setRemarks(request.getRemarks());

	    cardRepository.save(card);

	    Transaction savedTransaction = transactionRepository.save(transaction);

	    return transactionMapper.toResponse(savedTransaction);
	}

	@Override
	@Transactional(readOnly = true)
	public List<TransactionResponse> getTransactionsByCard(Long cardId) {

	    if (!cardRepository.existsById(cardId)) {
	        throw new CardNotFoundException("Card not found with id: " + cardId);
	    }

	    return transactionRepository.findByCardId(cardId)
	            .stream()
	            .map(transactionMapper::toResponse)
	            .toList();
	}

}
