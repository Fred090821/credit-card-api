package com.example.credit_card_api.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class CreditCardRequest {

    @Size(min = 2, max = 50, message = "Card holder name must be between 2 and 100 characters")
    private String cardHolderName;

    @NotBlank(message = "Card number is required")
    @Pattern(regexp = "\\d{13,19}", message = "Card number must be 13-19 digits")
    private String cardNumber;

    @NotBlank(message = "Card limit is required")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "Card limit must be a positive number with up to 2 decimal places")
    private String cardLimit; // Stored as String for input

    public CreditCardRequest() {
    }

    // Existing constructor modified for String cardLimit
    public CreditCardRequest(String cardHolderName, String cardNumber, String cardLimit) {
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardLimit = cardLimit;
    }

    // Getters and Setters
    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(String cardLimit) {
        this.cardLimit = cardLimit;
    }

    // Conversion method for service layer
    @DecimalMin(value = "0.01", message = "Card limit must be greater than zero")
    public BigDecimal getCardLimitAsBigDecimal() {
        try {
            return new BigDecimal(cardLimit);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid card limit format");
        }
    }

    // toString
    @Override
    public String toString() {
        return "CreditCardRequest{" +
            "cardHolderName='" + cardHolderName + '\'' +
            ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
            ", cardLimit='" + cardLimit + '\'' +
            '}';
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}