package com.company;

import java.util.concurrent.Semaphore;

/**
 * Created by danielslone on 4/22/17.
 */
public class ProcessThread extends Thread {
    private int threadId;
    private Semaphore semaphore;

    public ProcessThread(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    private int random(int n) {
        return (int) Math.round(n * Math.random());
    }

    private void busyCode() {
        int sleepPeriod = random(500);
        try {
            sleep(sleepPeriod);
        } catch (InterruptedException e) {
        }
    }

    private void noncriticalCode() {
        busyCode();
    }

    private void criticalCode() {
        busyCode();
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            try {
                semaphore.acquire();
                criticalCode();
                semaphore.release();
            } catch (InterruptedException e) {
                System.out.println("Exception " + e.toString());
            }
        }
        for (int i = 0; i < 3; i++) {
            noncriticalCode();
        }
    }
}