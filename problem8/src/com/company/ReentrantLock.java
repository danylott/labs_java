package com.company;

class ReentrantLock {
    private Thread lockedBy;
    private final Object monitor;

    public ReentrantLock() {
        this.lockedBy = null;
        this.monitor = new Object();
    }

    public void lock() {
        synchronized (this.monitor) {
            try {
                while (this.lockedBy != null && this.lockedBy != Thread.currentThread()) {
                    this.monitor.wait();
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.lockedBy = Thread.currentThread();
        }
    }

    public void unlock() {
        synchronized (this.monitor) {
            if (this.lockedBy == Thread.currentThread()) {
                this.lockedBy = null;
                this.monitor.notify();
            }
        }
    }
}