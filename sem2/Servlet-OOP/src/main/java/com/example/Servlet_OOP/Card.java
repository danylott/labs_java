package com.example.Servlet_OOP;

import lombok.Data;

import java.util.List;

@Data
public class Card {
    private final String name;
    private final boolean blocked;
    private final Account account;


    public Card(int id, boolean blocked, String name, int balance, List<Payment> payments) {
        this.name = name;
        this.blocked = blocked;
        account = new Account(id, balance, payments);
    }

    public Card(String name, boolean blocked, Account account) {
        this.name = name;
        this.blocked = blocked;
        this.account = account;
    }

    //public Card(String name, boolean blocked)


    public String getName() {
        return name;
    }

    public int getId() {
        return account.getId();
    }

    public int getBalance() {
        return account.getBalance();
    }

    public List<Payment> getPayments() {
        return account.getPayments();
    }

    public boolean isBlocked() {
        return blocked;
    }


    @Override
    public String toString() {
        return name + ": " + account.getBalance();
    }

    public Account getAccount() {
        return account;
    }
}
