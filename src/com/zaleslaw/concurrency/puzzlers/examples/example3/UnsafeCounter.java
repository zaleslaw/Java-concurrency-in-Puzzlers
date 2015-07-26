package com.zaleslaw.concurrency.puzzlers.examples.example3;

/**
 * The result is equal 0 for all starts' cause counter has primitive type
 */
public class UnsafeCounter {

    public int counter = 0;

    public static void main(String[] args) throws InterruptedException {

        UnsafeCounter b = new UnsafeCounter();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    synchronized (b) {
                        b.counter++;
                    }

            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    synchronized (b) {
                        b.counter--;
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
