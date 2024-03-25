package com.example.new_cards.dto;

import com.example.new_cards.entities.Card;

public class DTOTransformers {

    public static CardDTO cardToDTO(Card card) {
        return new CardDTO.CardDTOBuilder()
                .name(card.getCardName())
                .id(Long.toString(card.getId()))
                .colour(card.getCardColour())
                .status(card.getCardStatus().name())
                .cardOwner(card.getCardOwner().getEmail())
                .description(card.getDescription())
                .date(card.getDateOfCreation())
                .build();
    }
}
