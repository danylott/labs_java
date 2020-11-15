package main;

import java.util.ArrayList;

public class Base extends ArrayList {
    public String id;
    public Base(String id) {
        this.id = id;
    }

    public void printHelloWorld() {
        System.out.println("Hello World!");
    }
}
