package com.example.credit_card_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.credit_card_api.model.CreditCard;
import com.example.credit_card_api.model.CreditCardRequest;
import com.example.credit_card_api.repository.CreditCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CreditCardControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private CreditCardRepository creditCardRepository;

  @BeforeEach
  void setUp() {
    creditCardRepository.deleteAll();
  }

  @AfterEach
  void tearDown() {
    creditCardRepository.deleteAll();
  }

  @Test
  void addCreditCard_ValidRequest_ReturnsCreated() throws Exception {
    // Arrange
    CreditCardRequest request = new CreditCardRequest(
        "John Doe",
        "4111111111111111", // Valid Luhn 10 number
        "1000.00"
    );

    // Act & Assert
        mockMvc.perform(post("/api/credit-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.cardHolderName").value("John Doe"))
        .andExpect(jsonPath("$.cardNumber").value("4111111111111111"))
        .andExpect(jsonPath("$.cardLimit").value(1000.00))
        .andExpect(jsonPath("$.balance").value(0.00))
        .andReturn();

    // Verify database
    List<CreditCard> cards = creditCardRepository.findAll();
    assertEquals(1, cards.size());
    assertEquals("John Doe", cards.get(0).getCardHolderName());
  }

  @Test
  void addCreditCard_InvalidLuhn_ReturnsBadRequest() throws Exception {
    // Arrange
    CreditCardRequest request = new CreditCardRequest(
        "John Doe",
        "4111111111111112", // Invalid Luhn 10 number
        "1000.00"
    );

    // Act & Assert
    mockMvc.perform(post("/api/credit-cards")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Luhn 10 check failed")));

    // Verify nothing was saved
    assertEquals(0, creditCardRepository.count());
  }

  @Test
  void getAllCreditCards_NoCards_ReturnsEmptyList() throws Exception {
    // Act & Assert
    mockMvc.perform(get("/api/credit-cards"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(0));
  }

  @Test
  void getAllCreditCards_WithCards_ReturnsAllCards() throws Exception {
    // Arrange
    CreditCard card1 = new CreditCard("User 1", "4111111111111111", new BigDecimal("1000.00"));
    CreditCard card2 = new CreditCard("User 2", "5555555555554444", new BigDecimal("2000.00"));
    creditCardRepository.saveAll(List.of(card1, card2));

    // Act & Assert
    mockMvc.perform(get("/api/credit-cards"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].cardHolderName").value("User 1"))
        .andExpect(jsonPath("$[1].cardHolderName").value("User 2"));
  }

  //Other tests to add:
  //addCreditCard_InvalidRequest_ReturnsValidationErrors
  //addCreditCard_DuplicateCard_ReturnsBadRequest

}