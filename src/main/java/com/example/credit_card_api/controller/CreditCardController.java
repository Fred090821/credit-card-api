package com.example.credit_card_api.controller;

import com.example.credit_card_api.model.CreditCard;
import com.example.credit_card_api.model.CreditCardRequest;
import com.example.credit_card_api.service.CreditCardService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credit-cards")
public class CreditCardController {

    private static final Logger log = LoggerFactory.getLogger(CreditCardController.class);

    private final CreditCardService creditCardService;

    public CreditCardController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    /**
     * Adds a new credit card based on the provided request.
     *
     * @param creditCardRequest the request containing credit card details to be added
     * @return a ResponseEntity containing the added CreditCard object and HTTP status CREATED
     */
    @PostMapping
    public ResponseEntity<CreditCard> addCreditCard(@Valid @RequestBody CreditCardRequest creditCardRequest) {
        log.info("Received credit card request: {}", creditCardRequest);
        CreditCard creditCard = creditCardService.addCreditCard(creditCardRequest);
        return new ResponseEntity<>(creditCard, HttpStatus.CREATED);
    }

    /**
     * Retrieves all credit cards stored in the system.
     *
     * Endpoint: GET /api/credit-cards
     * Produces: application/json
     *
     * @return ResponseEntity containing:
     *         - HTTP Status 200 (OK) on success
     *         - List<CreditCard> as JSON in the response body
     *         - Empty list if no credit cards exist
     */
    @GetMapping
    public ResponseEntity<List<CreditCard>> getAllCreditCards() {
        log.info("Retrieving all credit cards");
        List<CreditCard> creditCards = creditCardService.getAllCreditCards();
        log.info("Retrieving all credit cards : {}", creditCards);
        return new ResponseEntity<>(creditCards, HttpStatus.OK);
    }
}