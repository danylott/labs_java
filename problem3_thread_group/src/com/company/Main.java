package com.company;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        ThreadGroup group1 = new ThreadGroup("G1");
        ThreadGroup group2 = new ThreadGroup(group1, "G2");
        ThreadGroup group3 = new ThreadGroup(group2, "G3");
        ThreadGroup group4 = new ThreadGroup("G4");

        new Thread(group1, new SingleRunnable(2000)).start();
        new Thread(group1, new SingleRunnable(3000)).start();
        new Thread(group2, new SingleRunnable(4000)).start();
        new Thread(group2, new SingleRunnable(5000)).start();
        new Thread(group2, new SingleRunnable(6000)).start();
        new Thread(group3, new SingleRunnable(7000)).start();
        new Thread(group3, new SingleRunnable(10000)).start();
        new Thread(group4, new SingleRunnable(9000)).start();
        new Thread(group4, new SingleRunnable(2000)).start();
        new Thread(group4, new SingleRunnable(5000)).start();

        displayThreads.printAllThreads(group3);
    }
}
