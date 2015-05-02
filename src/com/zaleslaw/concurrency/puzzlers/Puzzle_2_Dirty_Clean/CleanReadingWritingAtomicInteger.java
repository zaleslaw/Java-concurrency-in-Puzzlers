package com.zaleslaw.concurrency.puzzlers.Puzzle_2_Dirty_Clean;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger provides atomic operations of increment and decrement
 */
public class CleanReadingWritingAtomicInteger {

    public AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        CleanReadingWritingAtomicInteger b = new CleanReadingWritingAtomicInteger();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    b.counter.incrementAndGet();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    b.counter.decrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Counter = " + b.counter);

    }
}
