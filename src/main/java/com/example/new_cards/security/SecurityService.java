package com.example.new_cards.security;

import com.example.new_cards.config.JwtService;
import com.example.new_cards.dto.CardDTO;
import com.example.new_cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final JwtService jwtService;
    private final CardService cardService;

    public String getUserName(String jwtToken) {
        jwtToken = jwtToken.substring(7);
        return jwtService.extractUsername(jwtToken);
    }

    public boolean isResourceOwner(Long cardId) {
        String loggedUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        CardDTO cardDTO = cardService.findCardById(cardId);
        return cardDTO != null ?
                loggedUserName.equals(cardDTO.getCardOwner())
                : true; // if value not exists, will be 404
    }
}
