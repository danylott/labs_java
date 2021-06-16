package com.lab2.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cards")
@NoArgsConstructor
public class Card {
    @Id
    @Column(name = "card_number", nullable = false)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "account_id", updatable = false, nullable = false)
    private Account account;
}
