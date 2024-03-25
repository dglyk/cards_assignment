package com.example.new_cards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardSearchDTO {
    private String id;
    private String name;
    private String colour;
    private String status;
    private String cardOwner;
    private String dateCreated;
    private String description;
    private String sortField;
    private String sortOrder;
}
