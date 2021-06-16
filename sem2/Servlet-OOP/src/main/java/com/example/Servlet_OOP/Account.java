package com.example.Servlet_OOP;

import lombok.Data;

import java.util.List;

@Data
public class Account {

    private int id;
    private int balance;
    private List<Payment> payments;


    public Account(int id, int balance, List<Payment> payments) {
        this.id = id;
        this.balance = balance;
        this.payments = payments;
    }


    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
}
