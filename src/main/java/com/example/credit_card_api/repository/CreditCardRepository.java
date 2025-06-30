package com.example.credit_card_api.repository;

import com.example.credit_card_api.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
    boolean existsByCardNumber(String cardNumber);
}