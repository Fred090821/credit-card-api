package com.example.credit_card_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_holder_name", nullable = false, length = 50)
    private String cardHolderName;

    @Column(name = "card_number", nullable = false, unique = true, length = 19)
    private String cardNumber;

    @Column(name = "card_limit", nullable = false, precision = 10, scale = 2)
    private BigDecimal cardLimit;

    @Column(name = "balance", nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal balance = BigDecimal.ZERO;


    public CreditCard() {
        this.balance = BigDecimal.ZERO; // Ensure £0 balance
    }

    public CreditCard(String cardHolderName, String cardNumber, BigDecimal cardLimit) {
        this();
        this.cardHolderName = cardHolderName;
        this.cardNumber = cardNumber;
        this.cardLimit = cardLimit;
    }

    @PrePersist
    protected void onCreate() {
        if (this.balance == null) {
            this.balance = BigDecimal.ZERO;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(BigDecimal cardLimit) {
        this.cardLimit = cardLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return Objects.equals(cardNumber, that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
            "id=" + id +
            ", cardHolderName='" + cardHolderName + '\'' +
            ", cardNumber='" + maskCardNumber(cardNumber) + '\'' +
            ", cardLimit=" + cardLimit +
            ", balance=" + balance +
            '}';
    }

    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}