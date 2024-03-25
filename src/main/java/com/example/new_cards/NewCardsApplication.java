package com.example.new_cards;

import com.example.new_cards.dto.CardDTO;
import com.example.new_cards.entities.Role;
import com.example.new_cards.entities.User;
import com.example.new_cards.repositories.UserRepository;
import com.example.new_cards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class NewCardsApplication {

    private static final Logger log = LoggerFactory.getLogger(NewCardsApplication.class);
    private final PasswordEncoder passwordEncoder;
    private final CardService cardService;


    public static void main(String[] args) {
        SpringApplication.run(NewCardsApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // save a few customers
            //only if the DB is empty
            if(!userRepository.findAll().iterator().hasNext()){
                //if db is empty, init users.
                User adminUser = new User();
                adminUser.setEmail("admin@admin.com");
                adminUser.setPassword(passwordEncoder.encode("admin"));
                adminUser.setRole(Role.ADMIN);
                userRepository.save(adminUser);

                User memberUser = new User();
                memberUser.setEmail("member1@members.com");
                memberUser.setPassword(passwordEncoder.encode("pass1"));
                memberUser.setRole(Role.MEMBER);
                userRepository.save(memberUser);

                User memberUser2 = new User();
                memberUser2.setEmail("member2@members.com");
                memberUser2.setPassword(passwordEncoder.encode("pass2"));
                memberUser2.setRole(Role.MEMBER);
                userRepository.save(memberUser2);
           
                //Save some cards.
                if(cardService.findAllCards().isEmpty()){
                    //Admin cards begin
                    CardDTO card1 = new CardDTO();
                    card1.setName("adminCard1");
                    card1.setCardOwner(adminUser.getEmail());
                    card1.setColour("#123ABC");
                    card1.setDescription("Description1");

                    CardDTO card2 = new CardDTO();
                    card2.setName("adminCard2");
                    card2.setCardOwner(adminUser.getEmail());
                    card2.setColour("#234BCD");
                    card2.setDescription("Description2");

                    CardDTO card3 = new CardDTO();
                    card3.setName("adminCard3");
                    card3.setCardOwner(adminUser.getEmail());
                    card3.setColour("#456FGH");
                    card3.setDescription("Description3");
                    
                    cardService.saveCard(card1);
                    cardService.saveCard(card2);
                    cardService.saveCard(card3);
                    //Admin cards end
                    
                    //Member 1 cards
                    CardDTO memberCard1 = new CardDTO();
                    memberCard1.setName("memberCard1");
                    memberCard1.setCardOwner(memberUser.getEmail());
                    memberCard1.setColour("#555FFF");
                    memberCard1.setDescription("Description1");

                    CardDTO memberCard2 = new CardDTO();
                    memberCard2.setName("memberCard2");
                    memberCard2.setCardOwner(memberUser.getEmail());
                    memberCard2.setColour("#666DDD");
                    memberCard2.setDescription("Description2");

                    CardDTO memberCard3 = new CardDTO();
                    memberCard3.setName("memberCard3");
                    memberCard3.setCardOwner(memberUser.getEmail());
                    memberCard3.setColour("#777HHH");
                    memberCard3.setDescription("Description3");

                    cardService.saveCard(memberCard1);
                    cardService.saveCard(memberCard2);
                    cardService.saveCard(memberCard3);
                }
            }

            // fetch all customers
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            userRepository.findAll().forEach(user -> {
                log.info(user.toString());
            });
            log.info("");
            
            log.info("All cards found with findAll():");
            log.info("-------------------------------");
            cardService.findAllCards().forEach(card -> {
                log.info(card.toString());
            });
        };
    }
}
