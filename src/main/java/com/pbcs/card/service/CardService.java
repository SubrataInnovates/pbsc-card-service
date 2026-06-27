package com.pbcs.card.service;

import java.util.List;



import com.pbcs.card.dto.request.CreateCardRequest;
import com.pbcs.card.dto.response.CardResponse;


public interface CardService
{
	 	CardResponse createCard(CreateCardRequest request);

	    CardResponse getCardById(Long id);

	    List<CardResponse> getAllCards();

	    void deleteCard(Long id);
	    
	    CardResponse activateCard(Long id);
	    
	    CardResponse blockCard(Long id);

}
