package com.pbcs.card.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbcs.card.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long>
{
	List<Transaction> findByCardId(Long cardId);
	Optional<Transaction> findByTransactionId(String transactionId);

}
