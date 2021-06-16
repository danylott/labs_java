package com.example.Servlet_OOP;

import java.util.List;
import java.util.Objects;

public class User {
    private String firstName;
    private String secondName;
    private String username;
    private String password;
    private List<Card> cards;

    public User(String firstName, String secondName, String username, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
    }

    public User(String username, List<Card> cards) {
        this.username = username;
        this.cards = cards;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, secondName, username, password);
    }
}
