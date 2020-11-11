package com.company;

import java.util.Arrays;

public class displayThreads {
    public static String countParents(ThreadGroup group){
        String str = "";
        ThreadGroup group_p = group.getParent();
        if (!group_p.getName().equals("main"))
            str += countParents(group_p) + "|    ";
        return str;
    }

    public static void printThread(ThreadGroup group) {
        while (group.activeCount() > 0) {
            try {
                Thread[] threads = new Thread[group.activeCount()];
                group.enumerate(threads);
                String levelSpace = countParents(group);
                System.out.println(levelSpace + "Inside " + group.getName() + ": " + "Threads: "
                        + Arrays.toString(threads) + " ");
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void printAllThreads(ThreadGroup group) throws InterruptedException {
        ThreadGroup group_p = group.getParent();
        if (!group_p.getName().equals("main")) {
            printAllThreads(group_p);
            Thread.sleep(500);
        }
        Runnable print = () -> {
            printThread(group);
        };
        Thread recordThread = new Thread(print);
        recordThread.start();
    }
}
