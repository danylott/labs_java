package com.lab2.demo.service;

import com.lab2.demo.converter.CardConverter;
import com.lab2.demo.exception.CardNumberNotNullException;
import com.lab2.demo.exception.UserNotFoundException;
import com.lab2.demo.service.data.CardService;
import com.lab2.demo.service.data.UserService;
import com.lab2.demo.dto.CardDTO;
import com.lab2.demo.entity.Account;
import com.lab2.demo.entity.Card;
import com.lab2.demo.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardControllerService {
    private final CardService cardService;
    private final UserService userService;

    private final CardConverter cardConverter;

    public CardDTO addCard(CardDTO cardDTO) {
        Card card = cardConverter.convertToEntity(cardDTO);
        Optional<User> user = userService.findUserByEmail(cardDTO.getUserEmail());
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with email " + cardDTO.getUserEmail() + " not found");
        }
        card.setUser(user.get());
        card.setAccount(new Account(null, cardDTO.getBalance(), cardDTO.isBlocked()));
        card = this.cardService.addCard(card);

        return cardConverter.convertToDTO(card);
    }

    public List<CardDTO> findByUser(String email) {
        return cardConverter.convertToListDTO(cardService.findByUser(email));
    }

    public CardDTO blockCard(String cardNumber) {
        if (cardNumber == null) {
            throw new CardNumberNotNullException();
        }

        return cardConverter.convertToDTO(cardService.blockCard(cardNumber));
    }

    public CardDTO unblockCard(String cardNumber) {
        if (cardNumber == null) {
            throw new CardNumberNotNullException();
        }
        return cardConverter.convertToDTO(cardService.unblockCard(cardNumber));
    }
}
