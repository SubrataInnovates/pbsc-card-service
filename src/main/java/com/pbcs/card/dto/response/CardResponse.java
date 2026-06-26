package com.pbcs.card.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pbcs.card.enums.CardStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponse {

    private Long id;

    private String cardNumber;

    private String cardHolderName;

    private Integer expiryMonth;

    private Integer expiryYear;

    private BigDecimal balance;

    private String currency;

    private CardStatus status;

    private LocalDateTime createdAt;

}