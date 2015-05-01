package com.zaleslaw.concurrency.puzzlers.Puzzle_4_Interruption_State;

/**
 * This class illustrates thread states (NEW, RUNNABLE, BLOCKED, TERMINATED) after different manipulation
 */
public class ThreadStates {

    public static Integer counter = 0;

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (counter < 1_000) {

                    synchronized (ThreadStates.class) {
                        counter++;
                        System.out.println("t1 is " + Thread.currentThread().getState());
                        if (Thread.currentThread().isInterrupted()) break;
                    }
                }
                System.out.println("t1 is " + Thread.currentThread().getState());
            }
        });


        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(10);
                    synchronized (ThreadStates.class) {
                        counter = counter - 1 + 1;
                        System.out.println("t1 (from t2 thread) is " + t1.getState());
                        Thread.sleep(100);
                        System.out.println("t1 (from t2 thread) is " + t1.getState());
                    }

                    t1.interrupt();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });


        System.out.println("t1 (from main thread) is " + t1.getState());
        t1.start();
        t2.start();
        System.out.println("t1 (from main thread) is " + t1.getState());
        t1.join();
        t2.join();
        System.out.println("t1 (from main thread) is " + t1.getState());

    }
}
