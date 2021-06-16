package com.example.Servlet_OOP;

public class Payment {
    private int pay;
    private String comment;

    public Payment(int pay, String comment) {
        this.pay = pay;
        this.comment = comment;
    }

    public int getPay() {
        return pay;
    }

    public String getComment() {
        return comment;
    }
}
