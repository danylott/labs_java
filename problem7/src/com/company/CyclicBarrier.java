package com.company;

import java.util.concurrent.BrokenBarrierException;

class CyclicBarrier {
    private final int numOfNeededElements;
    private int numberWaiting;
    private final Runnable barAction;
    private boolean broken;

    public CyclicBarrier(int numOfNeededElements, Runnable barAction) {
        this.numOfNeededElements = numOfNeededElements;
        this.numberWaiting = numOfNeededElements;
        this.barAction = barAction;
        this.broken = false;
    }

    public synchronized void await() throws InterruptedException, BrokenBarrierException {
        if (this.broken) {
            throw new BrokenBarrierException();
        }

        --this.numberWaiting;

        if (this.numberWaiting > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                this.broken = true;
                throw e;
            }
        } else {
            this.reset();
            notifyAll();
            if (this.barAction != null) {
                this.barAction.run();
            }
        }
    }

    public void reset() {
        this.numberWaiting = this.numOfNeededElements;
        this.broken = false;
    }

    public int getParties() {
        return this.numOfNeededElements;
    }
}
