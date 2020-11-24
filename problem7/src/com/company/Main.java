package com.company;
import java.util.concurrent.BrokenBarrierException;

public class Main {
    static private final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new BarAction());

    private static void startThread(long timeOutMillis, String name) {
        new Thread() {
            @Override
            public void run() {
                for(int i = 0; i < 2; i++) {
                    try {
                        System.out.println("Thread (" + name + ") started");
                        synchronized (this) {
                            this.wait(timeOutMillis);
                        }
                        System.out.println("Thread (" + name + ") stopped");
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(cyclicBarrier.getParties());
        startThread(5000, "first");
        startThread(10000, "second");
        startThread(3000, "third");
    }

    static class BarAction implements Runnable {
        public void run() {
            System.out.println("Barrier reached");
        }
    }
}