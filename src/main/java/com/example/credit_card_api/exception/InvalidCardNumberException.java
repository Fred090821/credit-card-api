package com.example.credit_card_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCardNumberException extends RuntimeException {
    public InvalidCardNumberException(String message) {
        super(message);
    }
}