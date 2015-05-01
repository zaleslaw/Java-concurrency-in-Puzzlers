package com.zaleslaw.concurrency.puzzlers.Puzzle_5_Static;

/**
 * Synchronized sections #1 and #2 install lock on static variable counter
 */
public class ChangeStaticCounterSynchronizedByObject {

    public static Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {

        ChangeStaticCounterSynchronizedByObject b = new ChangeStaticCounterSynchronizedByObject();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    synchronized (b) {   // #1
                        counter++;
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    synchronized (b) {   // #2
                        counter--;
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter = " + counter);

    }
}
