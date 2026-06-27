package com.pbcs.card.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pbcs.card.dto.request.CreateCardRequest;
import com.pbcs.card.dto.response.CardResponse;
import com.pbcs.card.mapper.CardMapper;
import com.pbcs.card.repository.CardRepository;
import com.pbcs.card.service.CardService;
import com.pbcs.card.util.CardNumberGenerator;
import com.pbcs.card.util.CvvGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService
{
	private final CardRepository cardRepository;
	private final CardMapper cardMapper;

	@Override
	public CardResponse createCard(CreateCardRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardResponse getCardById(Long id) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CardResponse> getAllCards()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCard(Long id) 
	{
		// TODO Auto-generated method stub
		
	}
	private String generateUniqueCardNumber()
	{
		String cardNumber;
		
		do
		{
			cardNumber=CardNumberGenerator.generate();
			
		}
		while(cardRepository.existsByCardNumber(cardNumber));
		return cardNumber;
		
	}
	private String generateCvv()
	{
		return CvvGenerator.generate();
	}

}
