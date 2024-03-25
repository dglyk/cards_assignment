package com.example.new_cards.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
public class Card {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User cardOwner;

    @NotBlank(message = "Name is mandatory")
    private String cardName;

    @Pattern(regexp = "^#[a-zA-Z0-9]{6}$", message = "colour should conform to a “6 alphanumeric characters prefixed with a #")
    private String cardColour;
    private CardStatus cardStatus;

    private String description;

    private String dateOfCreation;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(User cardOwner) {
        this.cardOwner = cardOwner;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardColour() {
        return cardColour;
    }

    public void setCardColour(String cardColour) {
        this.cardColour = cardColour;
    }

    public CardStatus getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(CardStatus cardStatus) {
        this.cardStatus = cardStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "CardName: " + this.cardName + "\nCardId: " + this.id + "\nCardOwner: " + this.cardOwner.getEmail();
    }
}
