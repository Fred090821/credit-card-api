package com.example.credit_card_api.service;

import com.example.credit_card_api.exception.DuplicateCardNumberException;
import com.example.credit_card_api.exception.InvalidCardNumberException;
import com.example.credit_card_api.model.CreditCard;
import com.example.credit_card_api.model.CreditCardRequest;
import com.example.credit_card_api.repository.CreditCardRepository;
import com.example.credit_card_api.util.Luhn10Validator;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreditCardServiceImpl implements CreditCardService {

    private static final Logger log = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    private final CreditCardRepository creditCardRepository;

    public CreditCardServiceImpl(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    @Override
    @Transactional
    public CreditCard addCreditCard(CreditCardRequest creditCardRequest) {
        log.info("Attempting to add a new credit card for: {}", creditCardRequest.getCardHolderName());
        log.debug("Received credit card request: {}", creditCardRequest);
        return creditCardRepository.save(getCreditCard(creditCardRequest));
    }

    private CreditCard getCreditCard(CreditCardRequest creditCardRequest) {
        String cardNumber = creditCardRequest.getCardNumber();

        if (!Luhn10Validator.isValid(cardNumber)) {
            log.warn("Luhn 10 validation failed for card number: {}", maskCardNumber(cardNumber));
            throw new InvalidCardNumberException("Invalid credit card number - Luhn 10 check failed");
        }
        if (creditCardRepository.existsByCardNumber(cardNumber)) {
            log.warn("Duplicate card number detected: {}", maskCardNumber(cardNumber));
            throw new DuplicateCardNumberException("Credit card number already exists");
        }

        CreditCard creditCard = new CreditCard(creditCardRequest.getCardHolderName(), cardNumber, new BigDecimal(creditCardRequest.getCardLimit()));

        log.debug("Constructed CreditCard entity: {}", creditCard);
        return creditCard;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditCard> getAllCreditCards() {
        log.info("Fetching all credit cards from the database");
        List<CreditCard> cards = creditCardRepository.findAll();
        log.debug("Retrieved {} credit cards", cards.size());
        return cards;
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}