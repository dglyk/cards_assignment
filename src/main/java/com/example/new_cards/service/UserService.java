package com.example.new_cards.service;

import com.example.new_cards.entities.Role;
import com.example.new_cards.entities.User;
import com.example.new_cards.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public boolean isLoggedUserAdmin() {
        Optional<User> user = findUserByEmail( SecurityContextHolder.getContext().getAuthentication().getName());
        if(user.isPresent()){
            return (user.get().getRole().equals(Role.ADMIN));
        }else {
            return false;
        }
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
