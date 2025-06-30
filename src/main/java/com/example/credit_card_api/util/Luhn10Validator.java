package com.example.credit_card_api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Hand-coded Luhn 10 algorithm implementation for credit card validation
 * Requirement: Hand code the Luhn 10 check, don't use a library
 */
@Component
public class Luhn10Validator {

    private static final Logger log = LoggerFactory.getLogger(Luhn10Validator.class);

    /**
     * Validates a credit card number using the Luhn 10 algorithm
     *
     * Algorithm:
     * 1. Starting from the rightmost digit (excluding check digit) and moving left,
     *    double the value of every second digit
     * 2. If the result of doubling is greater than 9, subtract 9
     * 3. Sum all digits
     * 4. If the total sum is divisible by 10, the number is valid
     *
     * @param cardNumber the credit card number to validate (digits only)
     * @return true if the card number passes Luhn 10 validation, false otherwise
     */
    public static boolean isValid(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            log.debug("Card number is null or empty");
            return false;
        }

        int sum = 0;
        boolean alternate = false;

        // Process digits from right to left
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (digit < 0 || digit > 9) {
                log.debug("Invalid character found in card number at position {}", i);
                return false;
            }

            if (alternate) {
                digit *= 2;

                // If doubling results in a two-digit number, subtract 9
                // This is equivalent to summing the digits (e.g., 18 -> 1+8 = 9, so 18-9 = 9)
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        boolean isValid = (sum % 10 == 0);

        log.debug("Luhn 10 validation for card ending in {}: {} (sum: {})",
            maskCardNumber(cardNumber), isValid ? "PASSED" : "FAILED", sum);

        return isValid;
    }

    /**
     * Masks card number for logging purposes
     */
    private static String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}