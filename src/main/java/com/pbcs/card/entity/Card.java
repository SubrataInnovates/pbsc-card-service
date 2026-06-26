package com.pbcs.card.entity;


import java.math.BigDecimal;

import com.pbcs.card.enums.CardStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="cards")
public class Card extends BaseEntity
{
	
	@Column(name = "card_number",nullable = false, unique = true, length = 16, updatable = false)
	private String cardNumber;

	@Column(name = "card_holder_name",nullable = false, length = 100)
	private String cardHolderName;
	
	@Column(name = "expiry_month",nullable = false)
	private Integer expiryMonth;

	@Column(name = "expiry_year",nullable = false)
	private Integer expiryYear;
	
	@Column(name="cvv",nullable = false,length= 3)
	private String cvv;
	
	@Column(name="balance",nullable = false, precision = 19, scale = 2)
	private BigDecimal balance;
	
	@Column(name="currency",nullable = false, length = 3)
	private String currency;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status",nullable = false)
	private CardStatus status;
	

	
	
}
