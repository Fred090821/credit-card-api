package com.example.credit_card_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateCardNumberException extends RuntimeException {
    public DuplicateCardNumberException(String message) {
        super(message);
    }
}