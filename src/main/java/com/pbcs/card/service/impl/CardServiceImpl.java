package com.pbcs.card.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pbcs.card.dto.request.CreateCardRequest;
import com.pbcs.card.dto.request.ReloadCardRequest;
import com.pbcs.card.dto.response.CardResponse;
import com.pbcs.card.dto.response.CardStatementResponse;
import com.pbcs.card.dto.response.TransactionResponse;
import com.pbcs.card.entity.Card;
import com.pbcs.card.enums.CardStatus;
import com.pbcs.card.exception.CardNotFoundException;
import com.pbcs.card.mapper.CardMapper;
import com.pbcs.card.mapper.TransactionMapper;
import com.pbcs.card.repository.CardRepository;
import com.pbcs.card.repository.TransactionRepository;
import com.pbcs.card.service.CardService;
import com.pbcs.card.util.CardNumberGenerator;
import com.pbcs.card.util.CvvGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    private final CardMapper cardMapper;
    private final TransactionMapper transactionMapper;

    @Override
    public CardResponse createCard(CreateCardRequest request) {

        Card card = cardMapper.toEntity(request);

        card.setCardNumber(generateUniqueCardNumber());
        card.setCvv(generateCvv());
        card.setBalance(request.getInitialBalance());
        card.setStatus(CardStatus.CREATED);

        Card savedCard = cardRepository.save(card);

        return cardMapper.toResponse(savedCard);
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse getCardById(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        return cardMapper.toResponse(card);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> getAllCards() {

        return cardRepository.findAll()
                .stream()
                .map(cardMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteCard(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        cardRepository.delete(card);
    }

    @Override
    @Transactional
    public CardResponse activateCard(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        if (card.getStatus() == CardStatus.ACTIVE) {
            return cardMapper.toResponse(card);
        }

        card.setStatus(CardStatus.ACTIVE);

        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse blockCard(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        if (card.getStatus() == CardStatus.BLOCKED) {
            return cardMapper.toResponse(card);
        }

        if (card.getStatus() == CardStatus.CREATED) {
            throw new IllegalStateException("Card must be activated before it can be blocked.");
        }

        if (card.getStatus() == CardStatus.CLOSED) {
            throw new IllegalStateException("Closed card cannot be blocked.");
        }

        card.setStatus(CardStatus.BLOCKED);

        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse unblockCard(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        if (card.getStatus() == CardStatus.ACTIVE) {
            return cardMapper.toResponse(card);
        }

        if (card.getStatus() == CardStatus.CREATED) {
            throw new IllegalStateException("Card must be activated before it can be unblocked.");
        }

        if (card.getStatus() == CardStatus.CLOSED) {
            throw new IllegalStateException("Closed card cannot be unblocked.");
        }

        if (card.getStatus() != CardStatus.BLOCKED) {
            throw new IllegalStateException("Only blocked cards can be unblocked.");
        }

        card.setStatus(CardStatus.ACTIVE);

        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse reloadCard(Long id, ReloadCardRequest request) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        if (card.getStatus() != CardStatus.ACTIVE) {
            throw new IllegalStateException("Only active cards can be loaded.");
        }

        card.setBalance(card.getBalance().add(request.getAmount()));

        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse closeCard(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        if (card.getStatus() == CardStatus.CLOSED) {
            throw new IllegalStateException("Card is already closed.");
        }

        if (card.getStatus() == CardStatus.EXPIRED) {
            throw new IllegalStateException("Expired card cannot be closed.");
        }

        card.setStatus(CardStatus.CLOSED);

        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional(readOnly = true)
    public CardStatementResponse getCardStatement(Long id) {

        Card card = cardRepository.findById(id)
                .orElseThrow(() ->
                        new CardNotFoundException("Card not found with id: " + id));

        List<TransactionResponse> transactions = transactionRepository
                .findByCardId(id)
                .stream()
                .map(transactionMapper::toResponse)
                .toList();

        return CardStatementResponse.builder()
                .cardId(card.getId())
                .cardNumber(card.getCardNumber())
                .cardHolderName(card.getCardHolderName())
                .balance(card.getBalance())
                .currency(card.getCurrency())
                .status(card.getStatus())
                .transactions(transactions)
                .build();
    }

    private String generateUniqueCardNumber() {

        String cardNumber;

        do {
            cardNumber = CardNumberGenerator.generate();
        } while (cardRepository.existsByCardNumber(cardNumber));

        return cardNumber;
    }

    private String generateCvv() {
        return CvvGenerator.generate();
    }
}