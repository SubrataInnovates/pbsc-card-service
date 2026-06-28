package com.pbcs.card.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.pbcs.card.dto.response.TransactionResponse;
import com.pbcs.card.entity.Transaction;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TransactionMapper {

    @Mapping(target = "cardId", source = "card.id")
    TransactionResponse toResponse(Transaction transaction);

}