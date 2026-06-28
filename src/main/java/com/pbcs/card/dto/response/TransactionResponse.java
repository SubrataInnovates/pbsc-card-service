package com.pbcs.card.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.pbcs.card.enums.TransactionStatus;
import com.pbcs.card.enums.TransactionType;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private String transactionId;

    private Long cardId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    private TransactionStatus transactionStatus;

    private String remarks;

    private LocalDateTime createdAt;

}