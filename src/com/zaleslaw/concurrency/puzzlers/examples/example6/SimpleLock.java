package com.zaleslaw.concurrency.puzzlers.examples.example6;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock is enabled in #1 and #3 and disabled in #2 and #4
 */
public class SimpleLock {

    public Integer counter = 0;
    public Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        SimpleLock b = new SimpleLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    b.lock.lock();  // #1
                    b.counter++;
                    b.lock.unlock();// #2
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    b.lock.lock();  // #3
                    b.counter--;
                    b.lock.unlock();// #4

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
