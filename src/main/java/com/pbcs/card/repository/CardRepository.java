package com.pbcs.card.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pbcs.card.entity.Card;

@Repository
public interface CardRepository extends JpaRepository<Card,Long>
{
	Optional<Card> findByCardNumber(String cardNumber);
	boolean existsByCardNumber(String cardNumber);

}
