package com.example.new_cards.repositories;

import com.example.new_cards.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    Optional<User> findById(Long aLong);


    Optional<User> findByEmail(String email);
}
