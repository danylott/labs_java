package com.lab2.demo.repository;

import com.lab2.demo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, String> {
    List<Card> findByUserEmail(String email);

    Optional<Card> findById(String id);
}
