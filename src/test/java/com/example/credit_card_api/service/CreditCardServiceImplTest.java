package com.example.credit_card_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.credit_card_api.model.CreditCard;
import com.example.credit_card_api.model.CreditCardRequest;
import com.example.credit_card_api.repository.CreditCardRepository;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CreditCardServiceImplTest {

  @Mock
  private CreditCardRepository creditCardRepository;

  @InjectMocks
  private CreditCardServiceImpl creditCardService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldReturnEmptyListWhenNoCreditCardsInDatabase() {
    // Arrange
    when(creditCardRepository.findAll()).thenReturn(Collections.emptyList());

    // Act
    List<CreditCard> result = creditCardService.getAllCreditCards();

    // Assert
    assertTrue(result.isEmpty(), "Expected an empty list when no credit cards are present in the database");
  }

  @Test
  void shouldReturnListOfAllCreditCardsWhenMultipleCardsExistInDatabase() {
    // Arrange
    CreditCard card1 = new CreditCard("John Doe", "1234567812345678", new BigDecimal("1000.00"));
    CreditCard card2 = new CreditCard("Jane Smith", "8765432187654321", new BigDecimal("2000.00"));
    when(creditCardRepository.findAll()).thenReturn(List.of(card1, card2));

    // Act
    List<CreditCard> result = creditCardService.getAllCreditCards();

    // Assert
    assertEquals(2, result.size(), "Expected list size to be 2 when two credit cards are present in the database");
    assertTrue(result.contains(card1), "Expected the list to contain the first credit card");
    assertTrue(result.contains(card2), "Expected the list to contain the second credit card");
  }

  @Test
  void shouldHandleDatabaseConnectionErrorsGracefullyAndLogMessage() {
    // Arrange
    when(creditCardRepository.findAll()).thenThrow(new RuntimeException("Database connection error"));

    // Act & Assert
    try {
      creditCardService.getAllCreditCards();
    } catch (Exception e) {
      assertTrue(e instanceof RuntimeException, "Expected a RuntimeException to be thrown");
      assertEquals("Database connection error", e.getMessage(), "Expected the exception message to match");
    }
  }

  @Test
  public void shouldSuccessfullyAddCreditCardWhenAllFieldsInCreditCardRequestAreValid() {
    // Arrange
    CreditCardRequest creditCardRequest = new CreditCardRequest("John Doe", "4532015112830366", "5000");
    CreditCard expectedCreditCard = new CreditCard("John Doe", "4532015112830366", new BigDecimal("5000"));

    when(creditCardRepository.existsByCardNumber("4532015112830366")).thenReturn(false);
    when(creditCardRepository.save(any(CreditCard.class))).thenReturn(expectedCreditCard);

    // Act
    CreditCard actualCreditCard = creditCardService.addCreditCard(creditCardRequest);

    // Assert
    assertNotNull(actualCreditCard);
    assertEquals(expectedCreditCard.getCardHolderName(), actualCreditCard.getCardHolderName());
    assertEquals(expectedCreditCard.getCardNumber(), actualCreditCard.getCardNumber());
    assertEquals(expectedCreditCard.getCardLimit(), actualCreditCard.getCardLimit());
    verify(creditCardRepository, times(1)).save(any(CreditCard.class));
  }

  @Test
  public void shouldThrowExceptionWhenCreditCardRequestIsNull() {
    // Arrange
    CreditCardRequest creditCardRequest = null;

    // Act & Assert
    assertThrows(NullPointerException.class, () -> creditCardService.addCreditCard(creditCardRequest));
  }
}
