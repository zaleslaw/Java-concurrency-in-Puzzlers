package com.zaleslaw.concurrency.puzzlers.Puzzle_4_Interruption_State;

/**
 * Thread tries to count before 1_000_000, but main thread want to finish after 500
 * <p>
 * Main thread change state of child tread to interrupted
 * <p>
 * Output is different from start to start
 */
public class SimpleThreadInterruption {
    public int counter;


    public static void main(String[] args) {
        SimpleThreadInterruption b = new SimpleThreadInterruption();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1_000_000; i++) {
                    System.out.println("State :" + Thread.currentThread().getState() + " with value " + b.counter++);
                    if (Thread.currentThread().isInterrupted()) break;
                }
            }
        });

        System.out.println("State before start:" + t1.getState());
        t1.start();

        while (b.counter < 100) {
            System.out.println(b.counter);
        }

        System.out.println("Interrupt thread");
        t1.interrupt();
        System.out.println("State after interrupting signal :" + t1.getState());

    }
}
