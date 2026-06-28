package com.pbcs.card.dto.response;

import java.math.BigDecimal;
import java.util.List;

import com.pbcs.card.enums.CardStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CardStatementResponse {

    private Long cardId;

    private String cardNumber;

    private String cardHolderName;

    private BigDecimal balance;

    private String currency;

    private CardStatus status;

    private List<TransactionResponse> transactions;
}