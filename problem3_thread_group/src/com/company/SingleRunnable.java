package com.company;

public class SingleRunnable implements Runnable{
    public int time;

    SingleRunnable(int time){
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
