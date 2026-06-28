package com.pbcs.card.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pbcs.card.dto.request.CreateCardRequest;
import com.pbcs.card.dto.request.ReloadCardRequest;
import com.pbcs.card.dto.response.CardResponse;
import com.pbcs.card.service.CardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController 
{
	private final CardService cardService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CardResponse createCard(@Valid @RequestBody CreateCardRequest request)
	{
		return cardService.createCard(request);
	}
	
	@GetMapping("/{id}")
	public CardResponse getCardById(@PathVariable Long id)
	{
		return cardService.getCardById(id);
	}
	@GetMapping
	public List<CardResponse> getAllCards()
	{
		return cardService.getAllCards();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCard(@PathVariable Long id)
	{
		cardService.deleteCard(id);
	}
	
	@PatchMapping("/{id}/activate")
	public CardResponse activateCard(@PathVariable Long id) {
	    return cardService.activateCard(id);
	}
	
	@PatchMapping("/{id}/block")
	public CardResponse blockCard(@PathVariable Long id) {
	    return cardService.blockCard(id);
	}
	@PatchMapping("/{id}/unblock")
	public CardResponse unblockCard(@PathVariable Long id) {
	    return cardService.unblockCard(id);
	}
	
	@PostMapping("/{id}/reload")
	public CardResponse reloadCard(@PathVariable Long id,@Valid @RequestBody ReloadCardRequest request) {

	    return cardService.reloadCard(id, request);
	}
	@PatchMapping("/{id}/close")
	public CardResponse closeCard(@PathVariable Long id) {
	    return cardService.closeCard(id);
	}

}
