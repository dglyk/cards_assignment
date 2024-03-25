package com.example.new_cards.entities.specifications;

import com.example.new_cards.entities.Card;
import org.springframework.data.jpa.domain.Specification;

public class CardSpecification {
    
    public static Specification<Card> filterByName(String cardName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cardName")), "%" + cardName.toLowerCase() + "%");
    }

    public static Specification<Card> filterBycolour(String colour) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cardColour")), "%" + colour.toLowerCase() + "%");
    }

    public static Specification<Card> filterByStatus(String cardStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("cardStatus")), "%" + cardStatus.toLowerCase() + "%");
    }

    public static Specification<Card> filterByDateCreated(String date) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("dateOfCreation")), date);
    }

    public static Specification<Card> filterByCardOwner(String userName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("cardOwner").get("email")), userName);
    }
}
