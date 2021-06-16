package com.lab2.demo.exception;

public class OutOfAccountBalanceException extends RuntimeException {
    public OutOfAccountBalanceException(String message) {
        super(message);
    }
}
