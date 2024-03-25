package com.example.new_cards.service;

import com.example.new_cards.dto.CardDTO;
import com.example.new_cards.dto.CardSearchDTO;
import com.example.new_cards.dto.DTOTransformers;
import com.example.new_cards.entities.Card;
import com.example.new_cards.entities.CardStatus;
import com.example.new_cards.entities.User;
import com.example.new_cards.entities.specifications.CardSpecification;
import com.example.new_cards.exceptions.NotFoundException;
import com.example.new_cards.repositories.CardRepository;
import com.example.new_cards.repositories.UserRepository;
import com.example.new_cards.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final UserService userService;
    private final CardRepository cardRepository;

    public CardDTO saveCard(CardDTO cardDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //Get logged in user
        Card card = new Card();
        Optional<User> user = userService.findUserByEmail(userName);
        card.setCardName(cardDTO.getName());
        if (user.isPresent()) {
            card.setCardOwner(user.get());
        } else {
            card.setCardOwner(null);
        }
        card.setDescription(cardDTO.getDescription());
        card.setCardColour(cardDTO.getColour());
        card.setCardStatus(CardStatus.TO_DO);
        card.setDateOfCreation(LocalDate.now().toString());
        card = cardRepository.save(card);

        return DTOTransformers.cardToDTO(card);
    }

    public List<CardDTO> findAllCards() {
        List<Card> allCards = new ArrayList<>();
        List<CardDTO> cardDTOList = new ArrayList<>();
        cardRepository.findAll().forEach(card -> cardDTOList.add(DTOTransformers.cardToDTO(card)));
        return cardDTOList;
    }

    public CardDTO findCardById(Long cardId) {
        Optional<Card> card = cardRepository.findById(cardId);
        if (card.isPresent()) {
            return DTOTransformers.cardToDTO(card.get());
        } else {
            return null;
        }
    }

    public CardDTO updateCard(CardDTO cardDTO) {

        if (cardDTO != null && cardDTO.getId() != null) {
            Optional<Card> card = cardRepository.findById(Long.valueOf(cardDTO.getId()));
            if (card.isPresent()) {
                Card c = card.get();
                c.setId(Long.valueOf(cardDTO.getId()));
                c.setCardStatus(cardDTO.getStatus() != null ? CardStatus.valueOf(cardDTO.getStatus()) : null);
                c.setDescription(cardDTO.getDescription() != null ? cardDTO.getDescription() : null);
                c.setCardColour(cardDTO.getColour() != null ? cardDTO.getColour() : null);
                // Date will not be updated, as is date of creation.
                if (cardDTO.getCardOwner() != null) {
                    c.setCardOwner(userService.findUserByEmail(cardDTO.getCardOwner()).get());
                }
                return DTOTransformers.cardToDTO(cardRepository.save(c));
            } else {
                return null;
            }
        } else {
            return cardDTO;
        }
    }

    public void deleteCard(Long cardId) throws NotFoundException {
        if(cardRepository.findById(cardId).isPresent()){
            cardRepository.deleteById(cardId);
        }else throw new NotFoundException("Did not delete card with id: " + cardId + " because it could not be found");
    }

    public List<CardDTO> retrieveCardDTOWithSpecifications (CardSearchDTO cardSearchDTO) {

        Specification<Card> specification = createFilteringSpecifications(cardSearchDTO);
        List<Card> results = cardRepository.findAll(specification, Sort.by(sortOrder(cardSearchDTO)));
        return results.stream().map(DTOTransformers::cardToDTO).toList();
    }

    private Specification<Card> createFilteringSpecifications(CardSearchDTO cardDtoInput) {
        Specification<Card> spec = Specification.where(null);

        if (cardDtoInput != null) {

            if (cardDtoInput.getName() != null) {
                spec = spec.and(CardSpecification.filterByName((cardDtoInput.getName())));
            }
            if (cardDtoInput.getColour() != null) {
                spec = spec.and(CardSpecification.filterBycolour((cardDtoInput.getColour())));
            }
            if (cardDtoInput.getStatus() != null) {
                spec = spec.and(CardSpecification.filterByStatus((cardDtoInput.getStatus())));
            }
            if (cardDtoInput.getDateCreated() != null) {
                spec = spec.and(CardSpecification.filterByDateCreated((cardDtoInput.getDateCreated())));
            }

            if(!userService.isLoggedUserAdmin()){
                String userName = SecurityContextHolder.getContext().getAuthentication().getName();
                //if a user is not admin, he can only search for his cards.
                spec = spec.and(CardSpecification.filterByCardOwner((userName)));
            }
        }
        return spec;
    }

    private Sort.Order sortOrder(CardSearchDTO dto) {
        Sort.Order sortOrder = new Sort.Order(Sort.Direction.ASC, "cardName");

        if (dto.getSortField() != null) {
            Sort.Direction sortDirection = Sort.Direction.ASC;
            if (!dto.getSortOrder().equals(Sort.Direction.ASC.name())) {
                sortDirection = Sort.Direction.DESC;
            }
            if (dto.getSortField() != null) {
                if (dto.getSortField().equals("cardName")) {
                    sortOrder = new Sort.Order(sortDirection, "cardName");
                } else if (dto.getSortField().equals("cardStatus")) {
                    sortOrder = new Sort.Order(sortDirection, "cardStatus");
                } else if (dto.getSortField().equals("cardColour")) {
                    sortOrder = new Sort.Order(sortDirection, "cardColour");
                }
            }
        }
        return sortOrder;
    }
}
