package com.example.new_cards.cards;

import com.example.new_cards.dto.CardDTO;
import com.example.new_cards.dto.CardSearchDTO;
import com.example.new_cards.entities.Card;
import com.example.new_cards.exceptions.NotFoundException;
import com.example.new_cards.security.SecurityService;
import com.example.new_cards.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final SecurityService securityService;

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/save")
    public CardDTO saveCard(@Valid @RequestBody CardDTO cardDTO) {
        return cardService.saveCard(cardDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allCards")
    public List<CardDTO> getAllCards() {
        return cardService.findAllCards();
    }

    @PreAuthorize("@securityService.isResourceOwner(#cardId) || hasRole('ADMIN')")
    @GetMapping("/getCard/{cardId}")
    public ResponseEntity getCard(@PathVariable Long cardId) {
        CardDTO result = cardService.findCardById(cardId);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Card id " + cardId + " does not exist");
        }
    }

    @PreAuthorize("@securityService.isResourceOwner(#cardDTO.id) || hasRole('ADMIN')")
    @PostMapping("/updateCard")
    public ResponseEntity updateCard(@Valid @RequestBody CardDTO cardDTO){
        CardDTO result = cardService.updateCard(cardDTO);
        if (result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Card can not be updated.");
        }
    }

    @PreAuthorize("@securityService.isResourceOwner(#cardId) || hasRole('ADMIN')")
    @PostMapping("/deleteCard/{cardId}")
    public ResponseEntity deleteCard(@PathVariable Long cardId){
        try{
            cardService.deleteCard(cardId);
            return ResponseEntity.status(HttpStatus.OK).body("Card with id " + cardId + " was successfully deleted");
        }catch (NotFoundException nfe){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nfe.getMessage());
        }

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    public ResponseEntity searchCards(@Valid @RequestBody CardSearchDTO cardSearchDTO){
            List<CardDTO> cards = cardService.retrieveCardDTOWithSpecifications(cardSearchDTO);
            return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
