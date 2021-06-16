package com.lab2.demo.service.data;

import com.lab2.demo.exception.CardAlreadyExistsException;
import com.lab2.demo.exception.CardNotFoundException;
import com.lab2.demo.exception.CardStateException;
import com.lab2.demo.repository.AccountRepository;
import com.lab2.demo.repository.CardRepository;
import com.lab2.demo.entity.Card;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public List<Card> findByUser(String email) {
        return cardRepository.findByUserEmail(email);
    }

    @Transactional
    public Card addCard(Card card) {
        Optional<Card> oldCard = cardRepository.findById(card.getId());
        if (oldCard.isPresent()) {
            throw new CardAlreadyExistsException("Card with id " + card.getId() + " already exists");
        }
        accountRepository.save(card.getAccount());
        log.info("card {} was created", card);
        return cardRepository.save(card);
    }

    @Transactional
    public Card blockCard(String cardNumber) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is blocked.");
        }
        log.info("card {} was blocked", cardNumber);
        c.getAccount().setBlocked(true);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

    @Transactional
    public Card unblockCard(String cardNumber) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (!c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is unblocked.");
        }
        log.info("card {} was unblocked", cardNumber);
        c.getAccount().setBlocked(false);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

    @Transactional
    public Card replenishAccount(String cardNumber, int amount) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is unblocked.");
        }

        int currentBalance = c.getAccount().getBalance();
        c.getAccount().setBalance(currentBalance + amount);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

}
