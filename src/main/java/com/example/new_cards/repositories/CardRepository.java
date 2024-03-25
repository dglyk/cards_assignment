package com.example.new_cards.repositories;

import com.example.new_cards.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends JpaRepository<Card, Long>, JpaSpecificationExecutor {

}
