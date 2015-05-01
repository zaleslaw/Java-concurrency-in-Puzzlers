package com.zaleslaw.concurrency.puzzlers.Puzzle_2_Dirty_Clean;

/**
 * The result is equal 0 for all starts' cause counter has primitive type
 */
public class DirtyReadingWritingPrimitiveType {

    public int counter = 0;

    public static void main(String[] args) {

        DirtyReadingWritingPrimitiveType b = new DirtyReadingWritingPrimitiveType();

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
