package com.zaleslaw.concurrency.puzzlers.Puzzle_2_Dirty_Clean;

/**
 * The final value of counter will be different from start to start
 */
public class DirtyReadingWritingObjectValue {

    public Integer counter = 0;

    public static void main(String[] args) {

        DirtyReadingWritingObjectValue b = new DirtyReadingWritingObjectValue();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    b.counter++;
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++)
                    b.counter--;
            }
        });

        t1.start();
        t2.start();

        System.out.println("Counter = " + b.counter);

    }
}
