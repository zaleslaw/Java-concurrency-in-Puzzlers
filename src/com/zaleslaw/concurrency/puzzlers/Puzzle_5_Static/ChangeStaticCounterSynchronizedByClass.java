package com.zaleslaw.concurrency.puzzlers.Puzzle_5_Static;

/**
 * Synchronized sections #1 and #2 through lock on Class.
 * <p>
 * It's recommended way.
 */
public class ChangeStaticCounterSynchronizedByClass {

    public static Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    synchronized (ChangeStaticCounterSynchronizedByClass.class) {   // #1
                        counter++;
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    synchronized (ChangeStaticCounterSynchronizedByClass.class) {   // #2
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
