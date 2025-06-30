package com.example.credit_card_api.service;

import com.example.credit_card_api.model.CreditCard;
import com.example.credit_card_api.model.CreditCardRequest;
import java.util.List;

public interface CreditCardService {

    /**
     * Adds a new credit card to the system based on the provided request details.
     *
     * @param creditCardRequest the request object containing details of the credit card to be added
     * @return the added CreditCard object containing the details of the newly added credit card
     */
    CreditCard addCreditCard(CreditCardRequest creditCardRequest);

    /**
     * Retrieves a list of all credit cards currently stored in the system.
     *
     * @return a list of CreditCard objects representing all stored credit cards
     */
    List<CreditCard> getAllCreditCards();
}