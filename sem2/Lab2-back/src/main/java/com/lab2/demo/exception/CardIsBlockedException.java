package com.lab2.demo.exception;

public class CardIsBlockedException extends RuntimeException {
    public CardIsBlockedException(String message) {
        super(message);
    }
}
