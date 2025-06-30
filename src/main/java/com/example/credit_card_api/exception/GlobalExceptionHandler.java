package com.example.credit_card_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ErrorResponse(String errorCode, String message) {}

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
            .findFirst()
            .map(error -> error.getDefaultMessage())
            .orElse("Validation failed");

        return ResponseEntity.badRequest()
            .body(new ErrorResponse("VALIDATION_FAILED", errorMessage));
    }

    @ExceptionHandler(InvalidCardNumberException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCardNumberException(InvalidCardNumberException ex) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse("INVALID_CARD_NUMBER", ex.getMessage()));
    }

    @ExceptionHandler(DuplicateCardNumberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCardNumberException(DuplicateCardNumberException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(new ErrorResponse("DUPLICATE_CARD", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred"));
    }
}