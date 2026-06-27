package com.pbcs.card.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReloadCardRequest 
{
	@NotNull(message="Amount is required")
	@DecimalMin(value = "0.01", message = "Reload amount must be greater than zero")
	private BigDecimal amount;
}
