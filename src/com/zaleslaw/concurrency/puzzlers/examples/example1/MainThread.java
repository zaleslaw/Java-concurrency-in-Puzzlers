package com.zaleslaw.concurrency.puzzlers.examples.example1;

public class MainThread {
    public static void main(String[] args) {
        OtherThread t = new OtherThread();
        t.start();

        synchronized (t) {
            try {
                System.out.println("Waiting for OtherThread to complete...");
                t.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Total is: " + t.total);
        }
    }
}

class OtherThread extends Thread {
    int total;

    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 100; i++) {
                total += i;
            }
            notify();
        }
    }
}