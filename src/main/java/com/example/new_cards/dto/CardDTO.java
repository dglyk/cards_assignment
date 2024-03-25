package com.example.new_cards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private String id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    @Pattern(regexp = "^#[a-zA-Z0-9]{6}$", message = "colour should conform to a “6 alphanumeric characters prefixed with a #")
    private String colour;
    @Pattern(regexp = "^(TO_DO|IN_PROGRESS|DONE)$", message = "Not a valid status. Status should be one of: TO_DO|IN_PROGRESS|DONE" )
    private String status;
    private String cardOwner;
    private String description;
    private String date;
}

