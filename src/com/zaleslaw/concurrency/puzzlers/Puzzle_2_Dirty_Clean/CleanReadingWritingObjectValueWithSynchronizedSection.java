package com.zaleslaw.concurrency.puzzlers.Puzzle_2_Dirty_Clean;

/**
 * Synchronized sections #1 and #2 install lock on counter changes
 */
public class CleanReadingWritingObjectValueWithSynchronizedSection {

    public Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {

        CleanReadingWritingObjectValueWithSynchronizedSection b = new CleanReadingWritingObjectValueWithSynchronizedSection();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++){
                    synchronized (b) {   // #1
                        b.counter++;
                    }
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++){
                    synchronized (b) {   // #2
                        b.counter--;
                    }
                }
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter = " + b.counter);

    }
}
