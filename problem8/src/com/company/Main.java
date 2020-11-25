package com.company;

public class Main {
    private static final int NUM_OF_ITERATIONS = 10;
    private static int counter = 0;
    private static final ReentrantLock Reentrant_Lock = new ReentrantLock();

    private static Runnable changeCount(int value, String name) {
        boolean locked = true;
        return locked ? () -> {
            for (int i = 0; i < NUM_OF_ITERATIONS; ++i) {
                Reentrant_Lock.lock();
                System.out.println(name);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter += value;
                Reentrant_Lock.unlock();
            }
        } : () -> {
            for (int i = 0; i < NUM_OF_ITERATIONS; ++i) {
                counter += value;
            }
        };
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(changeCount(1, "First"));
        Thread thread2 = new Thread(changeCount(-1, "Second"));

        thread1.setName("First");
        thread1.setName("Second");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(counter);
    }
}
